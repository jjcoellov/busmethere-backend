class UrlMappings {

	static mappings = {
        "/routes"(resources: "routeNumber") {
            "/stops"(resources: "stopLocation")
        }
        "/stoplocations"(resources: "stopLocation")

        "/bustracks"(resources: "busTrack")

        "/messages"(resources: "message") {
           "/comments"(resources:"comment")
         }

        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }
        "/"(view:"/index")
        "500"(view:'/error')
	}
}
