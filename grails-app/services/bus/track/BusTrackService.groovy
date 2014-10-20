package bus.track

import bus.route.Direction
import groovyx.net.http.RESTClient
import org.apache.http.HttpRequest
import org.apache.http.HttpRequestInterceptor
import org.apache.http.protocol.HttpContext
import org.springframework.scheduling.annotation.Async

class BusTrackService {

    static transactional = false
    def SUPPORTED_LINES = [1,2,3,4,8,9,12,13,14,15,16,19,21,23,25,27,28]

    def busTracker = new BusTracker()

    @Async
    void trackInformation() {

        SUPPORTED_LINES.each { lineNumber ->
            trackInformationByRouteNumberCode(lineNumber)
        }
    }

    def trackInformationByRouteNumberCode(def routeNumberCodeToTrack) {

        String serverName = "https://bus.data.je/"
        def restClient = new RESTClient(serverName)

        restClient.client.addRequestInterceptor(new HttpRequestInterceptor() {
            void process(HttpRequest httpRequest, HttpContext httpContext) {
                httpRequest.addHeader('Authorization', 'Basic ' + 'hackathondemo:hackathondemo'.bytes.encodeBase64().toString())
            }
        })

        def response = restClient.get(path: "/line/${routeNumberCodeToTrack}")

        response.data.each { responseItem ->

            def direction = getDirection(responseItem[0].MonitoredVehicleJourney.DirectionRef)
            def routeNumberCode = responseItem[0].MonitoredVehicleJourney.LineRef
            def lat = responseItem[0].MonitoredVehicleJourney.VehicleLocation.Latitude.toBigDecimal()
            def longitude = responseItem[0].MonitoredVehicleJourney.VehicleLocation.Longitude.toBigDecimal()
            def recordedAt = responseItem[0].RecordedAtTime
            def date = javax.xml.bind.DatatypeConverter.parseDateTime(recordedAt).getTime()
            def vehicleRef = responseItem[0].MonitoredVehicleJourney.VehicleRef

            def busTrack = new BusTrack(routeNumberCode,direction,lat,longitude,vehicleRef,date)
//            println """
//                   BusTrack created: recordedAt $recordedAt
//                   - routeNumber: ${routeNumberCode} - ${direction}
//                   - lat: ${lat}
//                   - lon: ${longitude}
//            """
            busTracker.offerBusToQueue(busTrack)
        }
    }

    Direction getDirection(String direction) {
        if (direction.toLowerCase() == 'inbound') {
            return Direction.INBOUND
        } else if (direction.toLowerCase() == 'outbound') {
            return Direction.OUTBOUND
        }
        throw new RuntimeException("direction (inbound|outbound) unexpected ")
    }

    public def getAllTracks(List<String> routeCodes, Integer maxTracks) {

        def linesToTrack = routeCodes?:SUPPORTED_LINES
        def result = []

        linesToTrack.each { lineNumber ->
            String routeCode = lineNumber.toString()
                def tracks = []
                for(int i=0; i < maxTracks; i++) {
                    def track = busTracker.pollBusFromQueue(routeCode)
                    if (track) {
                        tracks.add(track)
                    }
                }
                if (tracks) {
                    def busTrackerApiItem = new BusTrackApiObject(routeCode,tracks)
                    result.add(busTrackerApiItem)
                }
        }
        return result
    }
}
