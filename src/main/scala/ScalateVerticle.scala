import org.vertx.scala.core.eventbus.Message
import org.vertx.scala.core.json.{Json, JsonObject}
import org.vertx.scala.platform.Verticle

class ScalateVerticle extends Verticle {

  val converter = new ScalateConverter()

  val handler = (message: Message[JsonObject]) => {

    val title: String = message.body().getString("title")
    val uri: String = message.body().getString("uri")

    val response = Json.obj(
      "status" -> "ok",
      "layout" -> converter.convert(uri, title)
    )
    message.reply(response)
  }

  override def start(): Unit = {
    vertx.eventBus.registerHandler("scalate", handler)

    logger.info("scalate verticle start.")
  }

}
