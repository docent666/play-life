package models.com.bulba

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import models.com.bulba.canvas.{Array2dCanvas, Canvas, Finite2dCanvas, ArrayCanvas}

class Array2dCanvasSpec extends FlatSpec with ShouldMatchers {

  "2d array canvas" should "populate set of changed cells" in {
      val canvas = new Array2dCanvas[Seq[Cell], Seq[Seq[Cell]]](Seq(Seq(LiveCell, LiveCell), Seq(DeadCell, LiveCell), Seq(DeadCell, DeadCell)), Set((0,1), (0, 0), (1, 0), (1,1)))
      canvas.stage().changedCells should be(Set((1,0)))
  }

  "2d array canvas" should "not stage cells that have not changed since previous iteration" in {
    val canvas = new Array2dCanvas[Seq[Cell], Seq[Seq[Cell]]](Seq(Seq(LiveCell, LiveCell),
      Seq(DeadCell, LiveCell),
      Seq(DeadCell, DeadCell)),
      Set.empty)
    val stagedCanvas = canvas.stage()
    stagedCanvas.changedCells should be(Set.empty)

  }


}
