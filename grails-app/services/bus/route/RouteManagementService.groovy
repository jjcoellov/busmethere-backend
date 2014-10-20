package bus.route

import grails.transaction.Transactional

@Transactional
class RouteManagementService {

    Long estimateTimeForBusToArrive(String routeNumber, String stopCode, Direction direction) {
        RouteNumber.createCriteria()
    }
}
