package bus.track

import bus.route.Direction

class BusTrack {
    String routeNumberCode
    String direction
    BigDecimal latitude = 0
    BigDecimal longitude = 0
    Date recordedAt
    String vehicleRef

    BusTrack(String routeNumberCode, Direction direction, BigDecimal latitude, BigDecimal longitude, String vehicleRef = null, Date recordedAt = new Date()) {
        this.routeNumberCode = routeNumberCode
        this.direction = direction.toString()
        this.latitude = latitude.setScale(11)
        this.longitude = longitude.setScale(11)
        this.recordedAt = recordedAt
        this.vehicleRef = vehicleRef
    }

    public boolean equals(Object other) {
        if (other == null) return false
        if (this.is(other)) return true
        if (BusTrack != other.getClass()) return false
        if (vehicleRef != other.vehicleRef) return false

        return true
    }

    public int hashCode() {
        return recordedAt.hashCode();
    }
}
