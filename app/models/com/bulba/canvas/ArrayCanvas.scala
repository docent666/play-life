package models.com.bulba.canvas

import scala.concurrent.{ExecutionContext, Await, Future}
import scala.concurrent.duration._
import models.com.bulba.Cell
import ExecutionContext.Implicits.global

abstract class ArrayCanvas[S <: Seq[Cell], T <: Seq[S]] extends Canvas[S, T] {

  protected def stagedCells: Array[Array[Cell]] = {
    val newCanvas = Array.ofDim[Cell](canvas.length, canvas(0).length)
    val listOfFutures = for (i <- 0 until canvas.length) yield
      Future {
        for (j <- 0 until canvas(i).length)
          newCanvas(i)(j) = getCell(i, j).stage(getNeighbors(i, j), strategy)
      }
    Await.result(Future.sequence(listOfFutures), Duration(10, SECONDS))
    newCanvas
  }

}
