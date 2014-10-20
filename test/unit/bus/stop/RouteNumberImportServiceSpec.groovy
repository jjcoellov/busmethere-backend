package bus.stop

import bootstrap.RouteNumberImportService
import grails.test.mixin.TestFor
import org.apache.tools.ant.taskdefs.optional.junit.JUnitTest
import org.junit.Test

@TestFor(RouteNumberImportService)
class RouteNumberImportServiceSpec extends JUnitTest {

    @Test
    void "test to"() {

        String FILE_PATH = "bootstrap/bus/stop/staticData_20140515-1135.xml"
        InputStream csvInputStream = this.class.classLoader.getResourceAsStream(FILE_PATH)
        def file = new  XmlParser().parse(csvInputStream)


        def routeNumbers = file.Services.Service*.Lines*.Line*.LineName*.text()
        def services = []
        routeNumbers.each { routeNumberCode ->
            def servicesMatchingWithRouteNumber = file.Services.Service.findAll { it.Lines.Line[0].LineName.text() == routeNumberCode}
            if (servicesMatchingWithRouteNumber.size() != 1) {
                throw  new RuntimeException("unexpected error, services found doesn't match with the expected number")
            }
            services.add(servicesMatchingWithRouteNumber[0])
        }


        services.each { service ->
            def origin = service.StandardService.Origin.text()
            def destination = service.StandardService.Destination.text()

            //TODO create Route Number based on this service

            def outboundJourneys = service.StandardService.JourneyPattern.findAll { it.Direction.text() == 'outbound'}
            def inboundJourneys = service.StandardService.JourneyPattern.findAll { it.Direction.text() == 'inbound'}

            def outboundJourneysSectionsRefs = outboundJourneys*.JourneyPatternSectionRefs*.text()
            def outboundJourneysSections = file.JourneyPatternSections.JourneyPatternSection.findAll { outboundJourneysSectionsRefs.contains(it.'@id')}

            def inboundJourneysSectionsRefs = inboundJourneys*.JourneyPatternSectionRefs*.text()
            def inboundJourneysSections = file.JourneyPatternSections.JourneyPatternSection.findAll { inboundJourneysSectionsRefs.contains(it.'@id')}

            def outboundJourneySection = outboundJourneysSections[0]
            def inboundJourneySection = inboundJourneysSections[0]

            def firstOutboundStop = outboundJourneysSections.JourneyPatternTimingLink[0].From[0].StopPointRef[0].text()
            def outboundsTops = outboundJourneySection.JourneyPatternTimingLink*.To.StopPointRef*.text()

            //TODO create the RouteNumberStops associated to the current RouteNumber, looking the StopLocation by code, and Direction outBound

            def firstInboundStop = inboundJourneysSections.JourneyPatternTimingLink[0].From[0].StopPointRef[0].text()
            def inboundsTops = inboundJourneySection.JourneyPatternTimingLink*.To.StopPointRef*.text()

            //TODO create the RouteNumberStops associated to the current RouteNumber, looking the StopLocation by code, and Direction inbound

            println service

        }


        def service = services[0]
        def origin = service.StandardService.Origin.text()
        def destination = service.StandardService.Destination.text()

        //TODO create Route Number

        def outboundJourneys = service.StandardService.JourneyPattern.findAll { it.Direction.text() == 'outbound'}
        def inboundJourneys = service.StandardService.JourneyPattern.findAll { it.Direction.text() == 'inbound'}

        def outboundJourneysSectionsRefs = outboundJourneys*.JourneyPatternSectionRefs*.text()
        def outboundJourneysSections = file.JourneyPatternSections.JourneyPatternSection.findAll { outboundJourneysSectionsRefs.contains(it.'@id')}

        def inboundJourneysSectionsRefs = inboundJourneys*.JourneyPatternSectionRefs*.text()
        def inboundJourneysSections = file.JourneyPatternSections.JourneyPatternSection.findAll { inboundJourneysSectionsRefs.contains(it.'@id')}

        def outboundJourneySection = outboundJourneysSections[0]
        def inboundJourneySection = inboundJourneysSections[0]

        def firstOutboundStop = outboundJourneysSections.JourneyPatternTimingLink[0].From[0].StopPointRef[0].text()
        def outboundsTops = outboundJourneySection.JourneyPatternTimingLink*.To.StopPointRef*.text()

        //TODO create the RouteNumberStops associated to the current RouteNumber, looking the StopLocation by code, and Direction outBound

        def firstInboundStop = inboundJourneysSections.JourneyPatternTimingLink[0].From[0].StopPointRef[0].text()
        def inboundsTops = inboundJourneySection.JourneyPatternTimingLink*.To.StopPointRef*.text()

        //TODO create the RouteNumberStops associated to the current RouteNumber, looking the StopLocation by code, and Direction inbound



        println service
        assert file != null

    }

}
