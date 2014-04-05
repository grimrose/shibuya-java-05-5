import org.fusesource.scalate._


class ScalateSample {

  def layout(): String = {
    val bindings = Map(
      "name" -> List("Scala", "Java")
    )
    val engine = new TemplateEngine()
    engine.layout("sample.jade", bindings)
  }

}
