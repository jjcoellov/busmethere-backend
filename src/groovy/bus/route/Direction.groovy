package bus.route

public enum Direction {
    INBOUND,
    OUTBOUND

    static Direction fromString(String direction) {
        return values().find {it.name() == direction}
    }
}