package bus.route

class RouteNumberApiObject {
    String name
    String origin
    String destination
    String code

    List inboundStops
    List outboundStops

    RouteNumberApiObject(RouteNumber routeNumber, Boolean nested = false) {
        name = routeNumber.name
        origin = routeNumber.origin
        destination = routeNumber.destination
        code = routeNumber.code
        if (!nested) {
            inboundStops = routeNumber.inboundStops.collect { new StopLocationApiObject(it, true) }
            outboundStops = routeNumber.outboundStops.collect { new StopLocationApiObject(it, true) }
        }
    }
}
