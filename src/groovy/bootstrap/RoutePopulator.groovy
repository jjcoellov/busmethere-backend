package bootstrap

import bus.route.Direction
import bus.route.RouteNumber
import bus.route.RouteNumberStopLocation
import bus.stop.StopLocation

class RoutePopulator {

    static void createRoute1() {
        RouteNumber route1 = new RouteNumber(code: "1", origin: "Liberation Station", destination: "Gorey Pier")
        createOutboundRoute1Stops(route1)
        createInboundRoute1Stops(route1)
        route1.save(failOnError: true)
    }

    private static void createOutboundRoute1Stops(RouteNumber routeNumber) {
        List stopsOutbound = ["2465", "4542", "4525", "2846", "3496", "2342", "3646", "2875", "2747", "2682"]

        stopsOutbound.eachWithIndex { String stopCode, Integer sequenceNumber ->
            StopLocation stopLocation = StopLocation.findByCode(stopCode)
            new RouteNumberStopLocation(sequenceNumber: sequenceNumber + 1, routeNumber: routeNumber,
                    stopLocation: stopLocation, direction: Direction.OUTBOUND).save(failOnError: true)
        }
    }

    private static void createInboundRoute1Stops(RouteNumber routeNumber) {
        List stopsInbound = ["2682", "2746", "2937", "2863", "2735", "3724", "2728", "2734", "3729", "2465"]

        stopsInbound.eachWithIndex { String stopCode, Integer sequenceNumber ->
            StopLocation stopLocation = StopLocation.findByCode(stopCode)
            new RouteNumberStopLocation(sequenceNumber: sequenceNumber + 1, routeNumber: routeNumber,
                    stopLocation: stopLocation, direction: Direction.INBOUND).save(failOnError: true)
        }
    }
}
