package bus.track

class BusTrackApiObject {

    String routeCode
    List busTracks = []

    public BusTrackApiObject(String routeCode, BusTrack busTrack) {
        this.routeCode = routeCode
        this.busTracks.add(busTrack)
    }

    public BusTrackApiObject(String routeCode, List<BusTrack> busTracks) {
        this.routeCode = routeCode
        this.busTracks.addAll(busTracks)
    }
}
