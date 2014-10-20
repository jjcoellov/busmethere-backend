package messages

import bus.route.RouteNumber
import bus.stop.StopLocation

/**
 * Created by juanbarroso on 28/09/2014.
 */
class Message {

    String author
    String stopLocationCode
    String routeNumberCode
    String text
    Boolean sponsored = false
    String url
    Date dateCreated

    Integer likes = 0
    List messages

    static hasMany = [comments:Comment]


    static constraints = {
        stopLocationCode(nullable: true)
        routeNumberCode(nullable: true)
        url(maxSize:1000, nullable: true)
    }

}
