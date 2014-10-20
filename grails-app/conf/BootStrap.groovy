import bootstrap.RoutePopulator
import bus.route.RouteNumber
import bus.stop.StopLocation
import messages.Comment
import messages.Message

class BootStrap {

    def stopLocationImportService
    def routeNumberImportService

    def init = { servletContext ->
        def time = System.currentTimeMillis()
        log.info("Begin BootStrap (${this.class.name}}.")
        initialise(servletContext)
        def took = (System.currentTimeMillis() - time) / 1000
        log.info("BootStrap (${this.class.name}) completed in ${took} seconds.")
    }

    def initialise(def servletContext) {




        if (!StopLocation.count()) {
            stopLocationImportService.doImport()
        }
        if (!RouteNumber.count()) {
//            RoutePopulator.createRoute1()
            routeNumberImportService.doImport()
        }

        if (!Message.count()) {
            def m1 = new Message(author:"John", stopLocationCode: "2326", text: "Amazing holidays in Jersey!!", likes:10, url:"http://www.jerseyheritage.org/media/Find%20a%20Place%20to%20Visit%20TWO/3a%20Mont%20Orgueil%20John%20Lord.jpg?timestamp=1410048000045")
            def c1 = new Comment(author:"Julie", text:"Agree, now time to come back to home :(")
            m1.addToComments(c1)
            m1.save(failOnError: true)

            def m2 = new Message(author:"The Co-Operative", routeNumberCode: "1", text: "25% discount Cooperative",likes: 20, sponsored: true, url:"http://media.dontpayfull.com/media/deals/co-op-electrical-shop-uk-promo-code.jpg")
            def c2 = new Comment(author: "Peter", text: "Wowww, I have my voucher!! ^^")
            m2.addToComments(c2)
            m2.save(failOnError: true)

            new Message(author:"Chris", stopLocationCode: "2465", text: "Feeling Happy today, Good Morning to everybody :) !!", likes:10, url:"https://flic.kr/p/7KJ8MT").save(failOnError: true)

        }
    }
}
