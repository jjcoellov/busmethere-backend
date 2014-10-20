package messages

import grails.rest.RestfulController

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class MessageController extends RestfulController {

    static responseFormats = ['json']

    MessageController(){
        super(Message)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 1000, 1000)
        respond listAllResources(params), model: [("${resourceName}Count".toString()): countResources()]
    }

    protected List listAllResources(Map params) {
       List<Message> messages
       if (params.routeNumber) {
            messages = Message.findAllByRouteNumberCode(params.routeNumber)
        } else if (params.stopLocation) {
           messages = Message.findAllByStopLocationCode(params.stopLocation)
       } else {
           messages = Message.list(params)
       }
        return messages
    }
}
