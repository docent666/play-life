package models.com.bulba

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers


class GameStateSpec extends FlatSpec with ShouldMatchers {
  val entry1Canvas = """
    0000
    0010
    0110
    0000
                     """.stripMargin
  val result1Canvas = """
    0000
    0110
    0110
    0000
                      """.stripMargin

  val entry2Canvas = """
    0000
    0010
    0100
    0000
                     """.stripMargin
  val result2Canvas = """
    0000
    0000
    0000
    0000
                     """.stripMargin

  "GameState with String Canvas"  should "use the cell rules to advance a canvas (generally)" in {
    val canvas = new GameState(StringCanvas(entry1Canvas)).advance().canvas
    canvas should not be StringCanvas(entry1Canvas)
  }

  "GameState with String Canvas"  should "use the cell rules to advance a canvas (make cell alive)" in {
    val canvas = new GameState(StringCanvas(entry1Canvas)).advance().canvas
    val resultCanvas = StringCanvas(result1Canvas)
    canvas should be (resultCanvas)
  }

  "GameState with String Canvas"  should "use the cell rules to advance a canvas (kill starved cells)" in {
    val canvas = new GameState(StringCanvas(entry2Canvas)).advance().canvas
    val resultCanvas = StringCanvas(result2Canvas)
    canvas should be (resultCanvas)
    canvas should not be StringCanvas(entry2Canvas)
  }





}