package bus.route

import bus.stop.StopLocation

class StopLocationApiObject {
    String name
    String code
    String vixName
    BigDecimal latitude
    BigDecimal longitude

    List routes

    StopLocationApiObject(StopLocation stopLocation, Boolean nested = false) {
        name = stopLocation.name
        code = stopLocation.code
        vixName = stopLocation.vixName
        latitude = stopLocation.latitude
        longitude = stopLocation.longitude
        if (!nested) {
            routes = stopLocation.routeNumbers.collect { new RouteNumberApiObject(it, true) }
        }
    }
}
