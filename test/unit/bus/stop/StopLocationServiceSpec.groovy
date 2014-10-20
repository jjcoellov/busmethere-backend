package bus.stop

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(StopLocationService)
@Mock(StopLocation)
class StopLocationServiceSpec extends Specification {

    void "Test that given two stop locations, can calculate the closer"() {
        given:
        StopLocation stopLocationFurthest = new StopLocation(code: "1234", name: "aaaa", vixName: "aaaa", longitude: 100, latitude: 100).save(failOnError: true)
        StopLocation stopLocationClosest = new StopLocation(code: "2321", name: "sada", vixName: "asss", longitude: 30, latitude: 30).save(failOnError: true)

        when:
        Long closestStopId = service.getClosestStop(29, 29)

        then:
        closestStopId == stopLocationClosest.id
        closestStopId != stopLocationFurthest.id
    }

    void "Test closestStops list"() {
        given:
        StopLocation stopLocationClosest = new StopLocation(code: "2321", name: "sada", vixName: "asss", longitude: 30, latitude: 30).save(failOnError: true)
        StopLocation stopLocationFurthest = new StopLocation(code: "1234", name: "aaaa", vixName: "aaaa", longitude: 100, latitude: 100).save(failOnError: true)

        when:
        List<StopLocation> closestStops = service.getClosestStops(29, 29)

        then:
        closestStops.first().id == stopLocationClosest.id
        closestStops.first().id != stopLocationFurthest.id
    }
}
