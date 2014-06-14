package models.com.bulba.canvas

import scala.concurrent.{ExecutionContext, Await, Future}
import scala.concurrent.duration._
import models.com.bulba.Cell
import ExecutionContext.Implicits.global

abstract class VectorCanvas[S <: Seq[Cell], T <: Seq[S]] extends Canvas[S, T] {

  protected def stagedCells: Vector[Vector[Cell]] = {
    val listOfFutures = for (i <- 0 until canvas.length) yield
      Future {
        val row = (0 until canvas(i).length).foldLeft(Vector[Cell]()){(acc, j) =>
          haveNeighborsChanged(i, j) match {
            case true => acc :+ getCell(i, j).stage(getNeighbors(i, j), strategy)
            case false => acc :+ getCell(i, j)
          }
        }
        (i, row)
      }
    Await.result(Future.sequence(listOfFutures), Duration(10, SECONDS)).sortBy(_._1).map(_._2).toVector
  }

}
