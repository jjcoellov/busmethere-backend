package utility

import spock.lang.Specification

class GeographicalUtilitySpec extends Specification {
    void "Test getting the distance, in meters, between two longitude and latitude points."() {
        expect:
        result == GeographicalUtility.getDistanceFromPointsInMeters(latitude1, longitude1,
                                                                    latitude2, longitude2)
        where:
        latitude1     | longitude1    | latitude2     | longitude2    | result
        49.1837435378 | -2.1107483465 | 49.1997311894 | -2.0211396504 | 6.7500
        47.621800     | -122.350326   | 47.041917     | -122.893766   | 76.3866
    }
}
