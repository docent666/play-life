package models.com.bulba

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers


class Array3dCanvasSpec extends FlatSpec with ShouldMatchers{


  // D L L
  // L D D
  // D L D
  val canvasAbove = Seq(
    Seq(DeadCell, LiveCell, LiveCell),
    Seq(LiveCell, DeadCell, DeadCell),
    Seq(DeadCell, LiveCell, DeadCell))

  // L L L
  // L D D
  // D D L
  val canvasBelow = Seq(
    Seq(LiveCell, LiveCell, LiveCell),
    Seq(LiveCell, DeadCell, DeadCell),
    Seq(DeadCell, DeadCell, LiveCell))

  // D D D
  // D D L
  // D D D
  val canvas = Seq(
    Seq(DeadCell, DeadCell, DeadCell),
    Seq(DeadCell, DeadCell, LiveCell),
    Seq(DeadCell, DeadCell, DeadCell))

  val canvases = List(canvasBelow, canvas, canvasAbove)

  lazy val lay: Layers[Seq[Cell], Seq[Seq[Cell]]]  = new Layers(for (i <- 0 until 3) yield new Array3dCanvas(canvases(i), i, lay))

  "3d canvas" should "return correct neighbors" in {
    lay(1).getNeighbors(1,1) should be (Seq(
      DeadCell, LiveCell, LiveCell,
      LiveCell, DeadCell, DeadCell,
      DeadCell, LiveCell, DeadCell,

      DeadCell, DeadCell, DeadCell,
      DeadCell, LiveCell,
      DeadCell, DeadCell, DeadCell,

      LiveCell, LiveCell, LiveCell,
      LiveCell, DeadCell, DeadCell,
      DeadCell, DeadCell, LiveCell
    ))
  }

  "3d canvas" should "stage correctly" in {
    val staged = lay(1).stage()
    staged.getCell(1,1) should be (LiveCell)
    staged.getCell(1,2) should be (LiveCell)
    staged.getCell(0,2) should be (DeadCell)
  }

   "3d canvas" should "return dead neighbors for border cells" in {
     val deadCanvas = for (i <- 0 until 26) yield DeadCell
      lay(1).getNeighbors(-5, -5) should be (deadCanvas)
      lay(1).getNeighbors(10, 10) should be (deadCanvas)
  }

}
