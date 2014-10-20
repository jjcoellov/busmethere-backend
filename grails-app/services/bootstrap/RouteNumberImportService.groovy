package bootstrap

import bus.route.Direction
import bus.route.RouteNumber
import bus.route.RouteNumberStopLocation
import bus.stop.StopLocation
import grails.transaction.Transactional

@Transactional
class RouteNumberImportService {

    private static final String FILE_PATH = "bootstrap/bus/stop/staticData_20140515-1135.xml"

    def doImport() {
        def file = new XmlParser().parse(loadFileFromClassPath())

        def routeNumbers = file.Services.Service*.Lines*.Line*.LineName*.text()
        Map services = [:]
        routeNumbers.each { routeNumberCode ->
                def servicesMatchingWithRouteNumber = file.Services.Service.findAll {
                    it.Lines.Line[0].LineName.text() == routeNumberCode
                }
                if (servicesMatchingWithRouteNumber.size() != 1) {
                    throw new RuntimeException("unexpected error, services found doesn't match with the expected number")
                }
                services.put(routeNumberCode, servicesMatchingWithRouteNumber[0])
        }

        services.each { routeNumberCode, service ->
            try {
                def origin = service.StandardService.Origin.text()
                def destination = service.StandardService.Destination.text()

                RouteNumber routeNumber = new RouteNumber(code: routeNumberCode, origin: origin, destination: destination).save(failOnError: true)

                def outboundJourneys = service.StandardService.JourneyPattern.findAll { it.Direction.text() == 'outbound' }
                def inboundJourneys = service.StandardService.JourneyPattern.findAll { it.Direction.text() == 'inbound' }

                def outboundJourneysSectionsRefs = outboundJourneys*.JourneyPatternSectionRefs*.text()
                def outboundJourneysSections = file.JourneyPatternSections.JourneyPatternSection.findAll {
                    outboundJourneysSectionsRefs.contains(it.'@id')
                }

                def inboundJourneysSectionsRefs = inboundJourneys*.JourneyPatternSectionRefs*.text()
                def inboundJourneysSections = file.JourneyPatternSections.JourneyPatternSection.findAll {
                    inboundJourneysSectionsRefs.contains(it.'@id')
                }

                def outboundJourneySection = outboundJourneysSections[0]
                def inboundJourneySection = inboundJourneysSections[0]

                if (outboundJourneysSections .size() > 0) {
                    def firstOutboundStop = outboundJourneysSections.JourneyPatternTimingLink[0].From[0].StopPointRef[0].text()
                    def outboundStops = outboundJourneySection.JourneyPatternTimingLink*.To.StopPointRef*.text()

                    StopLocation firstOutboundStopLocation = StopLocation.findByCode(firstOutboundStop)
                    new RouteNumberStopLocation(sequenceNumber: 1, routeNumber: routeNumber, stopLocation: firstOutboundStopLocation, direction: Direction.OUTBOUND).save(failOnError: true)

                    outboundStops.eachWithIndex { outBoundStopCode, index ->
                        //index starts at 0 but we already have first stop, so should be + 2
                        Integer sequenceNumber = index + 2
                        StopLocation stopLocation = StopLocation.findByCode(outBoundStopCode)
                        if (stopLocation) {
                            new RouteNumberStopLocation(sequenceNumber: sequenceNumber, routeNumber: routeNumber, stopLocation: stopLocation, direction: Direction.OUTBOUND).save(failOnError: true)
                        } else {
                            log.info "****************************** $outBoundStopCode doesn't exist *******************************8"
                        }
                    }
                }

                if (inboundJourneysSections .size() > 0) {
                    def firstInboundStop = inboundJourneysSections.JourneyPatternTimingLink[0].From[0].StopPointRef[0].text()
                    def inboundStops = inboundJourneySection.JourneyPatternTimingLink*.To.StopPointRef*.text()

                    StopLocation firstInboundStopLocation = StopLocation.findByCode(firstInboundStop)
                    new RouteNumberStopLocation(sequenceNumber: 1, routeNumber: routeNumber, stopLocation: firstInboundStopLocation, direction: Direction.INBOUND).save(failOnError: true)

                    inboundStops.eachWithIndex { inBoundStopCode, index ->
                        //index starts at 0 but we already have first stop, so should be + 2
                        Integer sequenceNumber = index + 2
                        StopLocation stopLocation = StopLocation.findByCode(inBoundStopCode)
                        if (stopLocation) {
                            new RouteNumberStopLocation(sequenceNumber: sequenceNumber, routeNumber: routeNumber, stopLocation: stopLocation, direction: Direction.INBOUND).save(failOnError: true)
                        } else {
                            log.info "****************************** $inBoundStopCode doesn't exist *******************************8"
                        }
                    }
                }
            }
            catch (e) {
                log.error(e)
            }
        }
    }

    InputStream loadFileFromClassPath() {
        InputStream csvInputStream = this.class.classLoader.getResourceAsStream(FILE_PATH)
        return csvInputStream
    }
}
