import org.fusesource.scalate.TemplateEngine

class ScalateConverter {

  def convert(uri: String, title: String): String = {
    val bindings = Map(
      "slideTitle" -> title
    )
    val engine = new TemplateEngine()
    engine.layout(uri, bindings)
  }

}
