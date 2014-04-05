import org.vertx.scala.core.AsyncResult
import org.vertx.scala.core.buffer.Buffer
import org.vertx.scala.core.eventbus.Message
import org.vertx.scala.core.http.{HttpServer, RouteMatcher, HttpServerRequest}
import org.vertx.scala.core.json.{JsonObject, Json}
import org.vertx.scala.platform.Verticle

class Main extends Verticle {

  override def start(): Unit = {

    // scalate verticle
    val verticleName = "scala:" + classOf[ScalateVerticle].getName
    container.deployWorkerVerticle(name = verticleName, handler = (deployResult: AsyncResult[String]) => {
      if (deployResult.succeeded()) {
        val message = Json.obj(
          "title" -> "Do not use Vert.x in casual?",
          "uri" -> "slide.jade"
        )

        vertx.eventBus.send("scalate", message, (reply: Message[JsonObject]) => {
          val buffer = Buffer.apply(reply.body().getString("layout"))
          vertx.fileSystem.writeFile("index.html", buffer, (writeResult: AsyncResult[Void]) => {
            if (writeResult.succeeded()) {
              logger.info("create index.html")
            } else {
              logger.error(writeResult.cause())
              writeResult.cause().printStackTrace()
            }
          })
        })
      } else {
        logger.error(deployResult.cause())
        deployResult.cause().printStackTrace()
      }
    })

    // routes
    val matcher = RouteMatcher.apply()

    matcher.get("/", handler = (request: HttpServerRequest) => {
      request.response().sendFile("index.html")
    })

    // assets
    val pattern = "^\\/reveal\\/.*"
    matcher.getWithRegEx(pattern, handler = (request: HttpServerRequest) => {
      logger.info(request.path())
      request.response().sendFile(request.path().substring(1))
    })

    // http server
    val port = 9000

    val httpServer: HttpServer = vertx.createHttpServer()
    httpServer.requestHandler(matcher)
    httpServer.listen(port)

    logger.info("listen at http://localhost:9000")
  }

}
