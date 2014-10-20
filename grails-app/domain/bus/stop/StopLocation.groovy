package bus.stop

import bus.route.RouteNumber
import bus.route.RouteNumberStopLocation

class StopLocation {
    String code
    String name
    String vixName
    BigDecimal latitude
    BigDecimal longitude

    static transients = ['routeNumbers']

    static constraints = {
        latitude(scale: 11)
        longitude(scale: 11)
    }

    List<RouteNumber> getRouteNumbers() {
        List<RouteNumberStopLocation> routeNumberStopLocations = RouteNumberStopLocation.findAllByStopLocation(this)
        return routeNumberStopLocations*.routeNumber.unique()
    }
}
