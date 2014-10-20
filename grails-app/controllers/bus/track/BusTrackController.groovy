package bus.track

import bus.route.RouteNumberStopLocation
import grails.converters.JSON
import grails.transaction.Transactional

@Transactional(readOnly = true)
class BusTrackController  {

    static responseFormats = ['json']

    def afterInterceptor = [action: this.&refreshData]

    private refreshData(model) {
        busTrackService.trackInformation()
    }

    def busTrackService

    def index(Integer max) {
        params.max = Math.min(max ?: 1000, 1000)


//        busTrackService.trackInformation()

        params.max = Math.min(max ?: 1000, 1000)
        respond listAllResources(params)
    }


    protected List listAllResources(Map params) {
        params.maxTracksByRoute = Math.min(params.maxTracksByRoute ?: 3, 3)
        def routeNumbers
        if (params.stopCode) {
            routeNumbers = RouteNumberStopLocation.createCriteria().list {
                stopLocation {
                    eq "code", params.stopCode
                }
            }*.routeNumber*.code
        } else if (params.routeCode) {
            routeNumbers = [params.routeCode]
        }

        def listOfTracks = busTrackService.getAllTracks(routeNumbers, params.maxTracksByRoute)
        return listOfTracks
    }
}
