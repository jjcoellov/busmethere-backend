package bus.stop

import grails.transaction.Transactional

@Transactional
class StopLocationService {

    Long getClosestStop(BigDecimal latitudeToSearchBy, BigDecimal longitudeToSearchBy) {
        List<StopLocation> closestStops = getClosestStops(latitudeToSearchBy, longitudeToSearchBy)
        StopLocation closestStop = closestStops.first()
        return closestStop.id
    }

    List<StopLocation> getClosestStops(BigDecimal latitudeToSearchBy, BigDecimal longitudeToSearchBy) {
        Integer max = StopLocation.count()
        PriorityQueue closestStops = createPriorityQueue(latitudeToSearchBy, longitudeToSearchBy, max)

        StopLocation.list().each { stopLocation ->
            closestStops.add(stopLocation)
        }

        return closestStops.toList()
    }

    private createPriorityQueue(BigDecimal latitudeToSearchBy, BigDecimal longitudeToSearchBy, Integer max) {
        Comparator<StopLocation> comparator = new Comparator<StopLocation>() {
            @Override
            int compare(StopLocation location1, StopLocation location2) {
                calculateDistanceBetweenTwoPoints(latitudeToSearchBy, longitudeToSearchBy, location1.latitude, location1.longitude).abs() <=> calculateDistanceBetweenTwoPoints(latitudeToSearchBy, longitudeToSearchBy, location2.latitude, location2.longitude).abs()
            }
        }

        PriorityQueue priorityQueue = new PriorityQueue(max, comparator)
        return priorityQueue
    }

    private Double calculateDistanceBetweenTwoPoints(BigDecimal latitudeToSearchBy, BigDecimal longitudeToSearchBy, BigDecimal latitudeOfPoint, BigDecimal longitudeOfPoint) {
        return Math.sqrt(calculatePointsDifferenceSquared(latitudeOfPoint, latitudeToSearchBy) + calculatePointsDifferenceSquared(longitudeOfPoint, longitudeToSearchBy))
    }

    private BigDecimal calculatePointsDifferenceSquared(BigDecimal point1, BigDecimal point2) {
        return (point1-point2)*(point1-point2)
    }
}
