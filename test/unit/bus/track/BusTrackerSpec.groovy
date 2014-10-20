package bus.track

import bus.route.Direction
import spock.lang.Specification

class BusTrackerSpec extends Specification {

    BusTracker busTracker = new BusTracker()

    void "Test offering a Bus Track."() {
        given:
        BusTrack busTrack = new BusTrack("155", Direction.INBOUND, 100, 10)

        when:
        busTracker.offerBusToQueue(busTrack)

        then:
        busTracker.busTrackQueueMap.get(busTrack.routeNumberCode).first() == busTrack
    }

    void "Test prioritising a Bus Track."() {
        when:
        2.times {
            busTracker.offerBusToQueue(new BusTrack("155", Direction.INBOUND, 100, 10))
        }
        BusTrack prioritisedBusTrack = new BusTrack("111", Direction.INBOUND, 50, 51)
        prioritisedBusTrack.recordedAt -= 10
        busTracker.offerBusToQueue(prioritisedBusTrack)

        2.times {
            busTracker.offerBusToQueue(new BusTrack("155", Direction.INBOUND, 100, 10))
        }

        then:
        busTracker.peekBusFromQueue(prioritisedBusTrack.routeNumberCode) == prioritisedBusTrack
        busTracker.pollBusFromQueue(prioritisedBusTrack.routeNumberCode) == prioritisedBusTrack
        busTracker.pollBusFromQueue(prioritisedBusTrack.routeNumberCode) != prioritisedBusTrack
    }
}
