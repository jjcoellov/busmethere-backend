package bus.route

import bus.stop.StopLocation

class RouteNumber {

    String origin
    String destination
    String code

    static transients = ['name', 'inboundStops', 'outboundStops']

    String getName() {
        return "$origin - $destination"
    }

    List<StopLocation> getInboundStops() {
        List<RouteNumberStopLocation> routeNumberStopLocations = RouteNumberStopLocation.findAllByRouteNumberAndDirection(this, Direction.INBOUND)
        return routeNumberStopLocations*.stopLocation
    }

    List<StopLocation> getOutboundStops() {
        List<RouteNumberStopLocation> routeNumberStopLocations = RouteNumberStopLocation.findAllByRouteNumberAndDirection(this, Direction.OUTBOUND)
        return routeNumberStopLocations*.stopLocation
    }
}
