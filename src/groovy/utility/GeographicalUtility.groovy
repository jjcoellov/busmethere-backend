package utility

import java.math.RoundingMode

class GeographicalUtility {
    public static BigDecimal getDistanceFromPointsInMeters(BigDecimal latitude1, BigDecimal longitude1,
                                                           BigDecimal latitude2, BigDecimal longitude2) {
        BigDecimal earthRadius = 6371 // In kilometers
        BigDecimal latitudeDifference = Math.toRadians(latitude2 - latitude1)
        BigDecimal longitudeDifference = Math.toRadians(longitude2 - longitude1)
        BigDecimal a = Math.sin(latitudeDifference / 2) * Math.sin(latitudeDifference / 2) +
                       Math.cos(Math.toRadians(latitude1)) * Math.cos(Math.toRadians(latitude2)) *
                       Math.sin(longitudeDifference / 2) * Math.sin(longitudeDifference / 2)
        BigDecimal c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        BigDecimal distance = earthRadius * c

        return distance.setScale(4, RoundingMode.FLOOR)
    }
}
