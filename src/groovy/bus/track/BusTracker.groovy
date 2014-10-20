package bus.track

import bus.route.Direction

import java.util.concurrent.PriorityBlockingQueue

class BusTracker {

    final int QUEUE_SIZE = 10

    HashMap<String, PriorityBlockingQueue<BusTrack>> busTrackQueueMap = [:]

    void offerBusToQueue(BusTrack busTrack) {
        if (!busTrackQueueMap[busTrack.routeNumberCode]) {
            busTrackQueueMap[busTrack.routeNumberCode] = new PriorityQueue<>(QUEUE_SIZE, new Comparator<BusTrack>() {
                @Override
                int compare(BusTrack o1, BusTrack o2) {
                    o1.recordedAt <=> o2.recordedAt
                }
            })
        }
        PriorityQueue<BusTrack> busTrackPriorityQueue = busTrackQueueMap[busTrack.routeNumberCode]
        if (busTrackPriorityQueue.size() >= QUEUE_SIZE) {
            busTrackPriorityQueue.poll()
        }
        if (!busTrackPriorityQueue.contains(busTrack)) {
            busTrackPriorityQueue.offer(busTrack)
        }
    }

    BusTrack pollBusFromQueue(String routeNumberCode) {
        PriorityQueue<BusTrack> busTrackPriorityQueue = busTrackQueueMap[routeNumberCode]
        if (!busTrackPriorityQueue) {
            return null
        }
        return busTrackPriorityQueue.poll()
    }

    BusTrack peekBusFromQueue(String routeNumberCode) {
        PriorityQueue<BusTrack> busTrackPriorityQueue = busTrackQueueMap[routeNumberCode]
        if (!busTrackPriorityQueue) {
            return null
        }
        return busTrackPriorityQueue.peek()
    }


}
