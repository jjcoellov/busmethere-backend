package bus

import bus.track.BusTrackService
import groovyx.net.http.RESTClient
import org.apache.http.HttpRequest
import org.apache.http.HttpRequestInterceptor
import org.apache.http.protocol.HttpContext
import org.apache.tools.ant.taskdefs.optional.junit.JUnitTest
import org.junit.Test

class LiveDataTests extends JUnitTest {


    @Test
    void "test for api"() {
        def client
//        client = new HTTPBuilder('https://bus.data.je')
//        client.auth.basic 'hackathondemo', 'hackathondemo'
//        client.get(path:'/line/1')

//        client.get( path: '/line/1') { resp, json ->
//
//            println resp.status
//        }


//        def restClient = new RESTClient("https://bus.data.je/".toString())
//        //restClient.auth.basic("https://bus.data.je/",443,"hackathondemo","hackathondemo")
//
//        restClient.client.addRequestInterceptor(new HttpRequestInterceptor() {
//            void process(HttpRequest httpRequest, HttpContext httpContext) {
//                httpRequest.addHeader('Authorization', 'Basic ' + 'hackathondemo:hackathondemo'.bytes.encodeBase64().toString())
//            }
//        })
//
//
//        //def response = new BusTrackService().trackInformationByRouteNumberCode(1)
//
//        new BusTrackService().trackInformation()


    }


}
