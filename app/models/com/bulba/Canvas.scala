package models.com.bulba

import scala.util.Random
import scala.concurrent.{ExecutionContext, Await, Future}
import scala.concurrent.duration._
import ExecutionContext.Implicits.global

trait Canvas[+S <: Seq[Cell], +T <: Seq[S]] {

  val canvas: T

  protected implicit val strategy: StagingStrategy

  def getCell(x: Int, y: Int): Cell

  def getNeighbors(x: Int, y: Int): S

  override def toString: String = {
    canvas map (_.mkString("")) mkString "\n"
  }

  def toNumericSequence: Seq[Seq[Long]] = {

    def rowToSeqLong(row: Seq[Cell]): Seq[Long] = {
      if (row.length > 53) {
        val rows = row.splitAt(53)
        Seq(java.lang.Long.parseLong(rows._1.mkString, 2)) ++ rowToSeqLong(rows._2)
      } else Seq(java.lang.Long.parseLong(row.mkString, 2))
    }
    canvas.par.map(rowToSeqLong(_)).seq
  }

  def stage(): Canvas[S, T]

}

trait Finite3dCanvas[+S <: Seq[Cell], +T <: Seq[S]] extends Canvas[S, T] {
  def canvasBelow: Canvas[S, T]

  def canvasAbove: Canvas[S, T]

  protected implicit val strategy = Life3dStagingStrategy

  def getCell(x: Int, y: Int): Cell = (x, y) match {
    case (a, _) if a < 0 => DeadCell
    case (_, b) if b < 0 => DeadCell
    case (a, b) if a >= canvas.length || b >= canvas(a).length => DeadCell
    case (_, _) => canvas(x)(y)
  }

  def getNeighbors(x: Int, y: Int): S = {
    val neighbors = for {i1 <- x - 1 to x + 1; y1 <- y - 1 to y + 1; if !(i1 == x && y1 == y)} yield getCell(i1, y1)
    val belowCells = for {i1 <- x - 1 to x + 1; y1 <- y - 1 to y + 1} yield canvasBelow.getCell(i1, y1)
    val aboveCells = for {i1 <- x - 1 to x + 1; y1 <- y - 1 to y + 1} yield canvasAbove.getCell(i1, y1)
    (aboveCells ++ neighbors ++ belowCells).asInstanceOf[S]
  }


}

trait Finite2dCanvas[+S <: Seq[Cell], +T <: Seq[S]] extends Canvas[S, T] {

  protected implicit val strategy = Life2dStagingStrategy

  def getCell(x: Int, y: Int): Cell = (x, y) match {
    case (a, _) if a < 0 => DeadCell
    case (_, b) if b < 0 => DeadCell
    case (a, b) if a >= canvas.length || b >= canvas(a).length => DeadCell
    case (_, _) => canvas(x)(y)
  }

  def getNeighbors(x: Int, y: Int): S = (for {i1 <- x - 1 to x + 1
                                              y1 <- y - 1 to y + 1
                                              if !(i1 == x && y1 == y)}
  yield getCell(i1, y1)).asInstanceOf[S]

}

class DeadCanvas[+S <: Seq[Cell], +T <: Seq[S]] extends Canvas[S, T] {

  protected implicit val strategy = Life3dStagingStrategy

  override def getCell(x: Int, y: Int): Cell = DeadCell

  override def getNeighbors(x: Int, y: Int): S = Seq.empty[Cell].asInstanceOf[S]

  override def stage(): Canvas[S, T] = this

  override val canvas: T = Seq.empty[S].asInstanceOf[T]
}

case class RandomCanvas[S <: Seq[Cell], T <: Seq[S]](width: Int, height: Int) extends Finite2dCanvas[S, T] {
  def stage(): Canvas[S, T] = {
    new Array2dCanvas[S, T](canvas.asInstanceOf[T])
  }

  val canvas = (for (i <- 0 until width) yield
    for (y <- 0 until height) yield
      if (Random.nextInt(10) > 8) LiveCell else DeadCell).asInstanceOf[T]
}

class Random3dCanvas[S <: Seq[Cell], T <: Seq[S]](width: Int, height: Int, index: Int, layers: => Layers[S, T]) extends Array3dCanvas[S, T](Seq.empty[S].asInstanceOf[T], index, layers) {


  override def stage(): Canvas[S, T] = {
    new Array3dCanvas[S, T](canvas.asInstanceOf[T], index, layers)
  }

  override val canvas = (for (i <- 0 until width) yield
    for (y <- 0 until height) yield
      if (Random.nextInt(10) > 6) LiveCell else DeadCell).asInstanceOf[T]
}

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

class Array3dCanvas[S <: Seq[Cell], T <: Seq[S]](override val canvas: T, index: Int, layers: => Layers[S, T]) extends ArrayCanvas[S, T] with Finite3dCanvas[S, T] {


  override def canvasBelow: Canvas[S, T] = {
    layers.below(index)
  }

  override def canvasAbove: Canvas[S, T] = {
    layers.above(index)
  }

  def stage(): Canvas[S, T] = {
    new Array3dCanvas[S, T](stagedCells.map(_.toSeq).toSeq.asInstanceOf[T], index, layers)
  }
}

case class Array2dCanvas[S <: Seq[Cell], T <: Seq[S]](override val canvas: T) extends ArrayCanvas[S, T] with Finite2dCanvas[S, T] {
  def stage(): Array2dCanvas[S, T] = {
    new Array2dCanvas[S, T](stagedCells.map(_.toSeq).toSeq.asInstanceOf[T])
  }
}

case class StringCanvas(override val canvas: Seq[Seq[Cell]]) extends Finite2dCanvas[Seq[Cell], Seq[Seq[Cell]]] {
  def stage(): StringCanvas = {
    val allStagedCells = for (i <- 0 until canvas.length)
    yield (for (y <- 0 until canvas(i).length) yield getCell(i, y).stage(getNeighbors(i, y), Life2dStagingStrategy)).toSeq
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