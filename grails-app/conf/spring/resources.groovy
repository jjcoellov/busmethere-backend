import bus.route.RouteNumberApiObject
import bus.route.StopLocationApiObject
import grails.rest.render.json.JsonCollectionRenderer
import grails.rest.render.json.JsonRenderer
import messages.Message

// Place your Spring DSL code here
beans = {
    stopLocationApiObjectJSONRenderer(JsonRenderer, StopLocationApiObject) {
        excludes = ['class']
    }

    stopLocationApiObjectJSONCollectionRenderer(JsonCollectionRenderer, StopLocationApiObject) {
        excludes = ['class']
    }

    routeNumberApiObjectJSONRenderer(JsonRenderer, RouteNumberApiObject) {
        excludes = ['class']
    }

    routeNumberApiObjectJSONCollectionRenderer(JsonCollectionRenderer, RouteNumberApiObject) {
        excludes = ['class']
    }

    messageApiObjectJSONRenderer(JsonRenderer, Message) {
        excludes = ['class']
    }

    messageApiObjectJSONCollectionRenderer(JsonCollectionRenderer, Message) {
        excludes = ['class']
    }
}
