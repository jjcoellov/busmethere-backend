package bus.route

import grails.rest.RestfulController
import grails.transaction.Transactional

@Transactional(readOnly = true)
class RouteNumberController extends RestfulController {

    static responseFormats = ['json']

    RouteNumberController() {
        super(RouteNumber)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 1000, 1000)
        respond listAllResources(params), model: [("${resourceName}Count".toString()): countResources()]
    }

    protected RouteNumberApiObject queryForResource(Serializable id) {
        return new RouteNumberApiObject(RouteNumber.findByCode(id))
    }

    protected List listAllResources(Map params) {
        List<RouteNumber> routeNumbers
        if (params.routeCode) {
            routeNumbers = RouteNumber.findAllByCode(params.routeCode)
        } else if (params.stopCode) {
            routeNumbers = RouteNumberStopLocation.createCriteria().list {
                stopLocation {
                    eq "code", params.stopCode
                }
            }*.routeNumber
        } else {
            routeNumbers = RouteNumber.list(params)
        }
        return routeNumbers.collect { new RouteNumberApiObject(it) }
    }
}
