import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.StaticHandler
import io.vertx.ext.web.handler.sockjs.SockJSHandler
import io.vertx.ext.web.handler.sockjs.BridgeOptions
import io.vertx.core.http.HttpServer
import io.vertx.core.json.JsonObject

def router = Router.router(vertx)

BridgeOptions options = new BridgeOptions(new JsonObject([
  "outboundPermitteds" : [["address" : "feedback"]],
  "inboundPermitteds" :  [["address" : "feedback"]]
]));

router.route("/eventbus/*").handler(SockJSHandler.create(vertx).bridge(options));

router.route("/game/*").handler(StaticHandler.create("site"))

router.route("/api/votes").handler({ routingContext ->
	def response = routingContext.response()
	response.setChunked(true)
	vertx.eventBus().send("votes", null, { ar ->
		if (ar.succeeded()) {
			response.end(ar.result().body())
		} else {
			response.end("There was an error")
		}
	})
})

router.route("/api/standings").handler({ routingContext ->
	def response = routingContext.response()
	response.setChunked(true)
	vertx.eventBus().send("standings", null, { ar ->
		if (ar.succeeded()) {
			response.end(ar.result().body())
		} else {
			response.end("There was an error")
		}
	})
})

//How to do the same to get the users voting?? Question for the attendees


HttpServer httpServer = vertx.createHttpServer()

//Responder a cada petición con un Hello World!
httpServer.requestHandler(router.&accept)
httpServer.listen(8080)

