package models.com.bulba

import scala.util.Random
import scala.concurrent.{ExecutionContext, Await, Future}
import scala.concurrent.duration._
import ExecutionContext.Implicits.global

trait Canvas[+S <: Seq[Cell], +T <:Seq[S]] {

  def getCell(x: Int, y: Int): Cell

  def getNeighbors(x: Int, y: Int): S

  def stage() : Canvas[S, T]

  def toNumericSequence() : Seq[Seq[Long]]

}

trait FiniteCanvas[+S <: Seq[Cell], +T <: Seq[S]]  extends Canvas[S, T] {

  val canvas: T

  def getCell(x: Int, y: Int): Cell = (x, y) match {
    case (a, _) if a < 0 => DeadCell
    case (_, b) if b < 0 => DeadCell
    case (a, b) if a >= canvas.length || b >= canvas(a).length => DeadCell
    case (_, _) => canvas(x)(y)
  }

  def getNeighbors(x: Int, y: Int): S =   (for {i1 <- x - 1 to x + 1
                                                     y1 <- y - 1 to y + 1
                                                     if !(i1 == x && y1 == y)}
                                                           yield getCell(i1, y1)).asInstanceOf[S]


  def stage() : Canvas[S, T]

  override def toString : String = {
    canvas map(_.mkString("")) mkString "\n"
  }

  def toNumericSequence() :Seq[Seq[Long]] = {

    def rowToSeqLong(row : Seq[Cell]) : Seq[Long] = {
      if (row.length>53) {
        val rows = row.splitAt(53)
        Seq(java.lang.Long.parseLong(rows._1.mkString,2)) ++ rowToSeqLong(rows._2)
      } else Seq(java.lang.Long.parseLong(row.mkString,2))
    }
    canvas.par.map(rowToSeqLong(_)).seq
  }

}

case class RandomCanvas [S <: Seq[Cell], T <: Seq[S]] (width: Int, height :Int) extends FiniteCanvas[S, T] {
  def stage(): Canvas[S, T] = {
    new ArrayCanvas[S, T](canvas.asInstanceOf[T])
  }

  val canvas  = (for (i <- 0 until width) yield
    for (y <-0 until height) yield
      if (Random.nextInt(10)>8) LiveCell else DeadCell).asInstanceOf[T]
}

case class ArrayCanvas [S <: Seq[Cell], T <: Seq[S]] (override val canvas : T) extends FiniteCanvas[S, T] {
  def stage(): ArrayCanvas[S, T]= {
    val allStagedCells = {
      val newCanvas = Array.ofDim[Cell](canvas.length, canvas(0).length)
      val listOfFutures = for (i <- 0 until canvas.length) yield
        Future {   for (j <- 0 until canvas(i).length)
            newCanvas(i)(j)= getCell(i, j).stage(getNeighbors(i, j)) }
      Await.result(Future.sequence(listOfFutures), Duration(10, SECONDS))
      newCanvas
    }
    new ArrayCanvas[S, T](allStagedCells.map(_.toSeq).toSeq.asInstanceOf[T])
  }
}

case class StringCanvas (override val canvas: Seq[Seq[Cell]]) extends FiniteCanvas[Seq[Cell], Seq[Seq[Cell]]] {
  def stage(): StringCanvas = {
    val allStagedCells = for (i <- 0 until canvas.length)
      yield (for (y <- 0 until canvas(i).length) yield getCell(i, y).stage(getNeighbors(i, y))).toSeq
    new StringCanvas(allStagedCells.toSeq)
  }
}

object StringCanvas {
  def apply(stringCanvas: String) = {
    val parsedRows = stringCanvas.filter(x => x.equals('\n') || x.isDigit).split('\n').filterNot(_.isEmpty)
    val arrays = parsedRows.map(_.map {
      case '0' => DeadCell
      case '1' => LiveCell
      case _ => throw new IllegalArgumentException
    })
    new StringCanvas(arrays)
  }
}