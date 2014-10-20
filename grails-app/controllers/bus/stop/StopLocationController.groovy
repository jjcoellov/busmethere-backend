package bus.stop

import bus.route.Direction
import bus.route.RouteNumber
import bus.route.StopLocationApiObject
import grails.rest.RestfulController
import grails.transaction.Transactional

@Transactional(readOnly = true)
class StopLocationController extends RestfulController {

    def stopLocationService

    static responseFormats = ['json']

    StopLocationController() {
        super(StopLocation)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 1000, 1000)
        respond listAllResources(params), model: [("${resourceName}Count".toString()): countResources()]
    }

    protected Object queryForResource(Serializable id) {
        return new StopLocationApiObject(StopLocation.findByCode(id))
    }

    protected List listAllResources(Map params) {
        List<StopLocation> stopLocations
        if (params.code) {
            stopLocations = StopLocation.findAllByCode(params.code)
        } else if (params.lat && params.long) {
            BigDecimal latitude = params.lat.toBigDecimal()
            BigDecimal longitude = params.long.toBigDecimal()

            stopLocations = stopLocationService.getClosestStops(latitude, longitude)
        } else if (params.routeNumber && params.direction) {
            RouteNumber routeNumber = RouteNumber.findByCode(params.routeNumber)
            stopLocations = resolveStopsForDirection(params.direction, routeNumber)
        } else if (params.routeNumberId && params.direction) {
            RouteNumber routeNumber = RouteNumber.get(params.routeNumberId)
            stopLocations = resolveStopsForDirection(params.direction, routeNumber)
        } else {
            stopLocations = StopLocation.list(params)
        }
        return stopLocations.collect { new StopLocationApiObject(it) }
    }

    private resolveStopsForDirection(String directionString, RouteNumber routeNumber) {
        List<StopLocation> stopLocations = []
        Direction direction = Direction.fromString(directionString)
        if (direction == Direction.INBOUND) {
            stopLocations = routeNumber.inboundStops
        } else if (direction == Direction.OUTBOUND) {
            stopLocations = routeNumber.outboundStops
        }
        return stopLocations
    }
}
