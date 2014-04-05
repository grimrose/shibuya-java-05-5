import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith

import org.scalatest.FunSpec

@RunWith(classOf[JUnitRunner])
class ScalateSampleSpec extends FunSpec {

  describe("sample") {
    it("expect sample return instance") {
      val sut = new ScalateSample
      assert(sut != null)
    }

    it("expect layout to be equal") {
      val sut = new ScalateSample
      val actual = sut.layout()
      println(actual)
      val expect: String = """<!DOCTYPE html>
<html lang="jp">
  <head>
    <meta charset="utf-8"/>
    <title>タイトル</title>
  </head>
  <body>
    <h1>Jade - こんにちわ</h1>
    <div id="container">
      <p>Scala</p>
      <p>Java</p>
    </div>
  </body>
</html>
"""
      assert(actual.toString == expect)
    }

  }

}
