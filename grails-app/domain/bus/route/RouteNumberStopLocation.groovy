package bus.route

import bus.stop.StopLocation

class RouteNumberStopLocation {
    Integer sequenceNumber
    RouteNumber routeNumber
    StopLocation stopLocation
    Direction direction

    static mapping = {
        routeNumber cascade: 'all-delete-orphan'
    }
}