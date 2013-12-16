package models.com.bulba

import scala.util.Random

trait Canvas[T <: Cell] {

  def getCell(x: Int, y: Int): Cell

  def getNeighbors(x: Int, y: Int): Seq[T]

  def stage() : Canvas[Cell]

  def toNumericSequence() : Seq[Seq[Long]]

}

trait FiniteCanvas extends Canvas[Cell] {

  val canvas: Seq[Seq[Cell]]

  def getCell(x: Int, y: Int): Cell = (x, y) match {
    case (a, _) if a < 0 => DeadCell
    case (_, b) if b < 0 => DeadCell
    case (a, b) if a >= canvas.length || b >= canvas(a).length => DeadCell
    case (_, _) => canvas(x)(y)
  }

  def getNeighbors(x: Int, y: Int): Seq[Cell] = for (i1 <- x - 1 to x + 1;
                                                     y1 <- y - 1 to y + 1
                                                     if !(i1 == x && y1 == y))
  yield getCell(i1, y1)


  def stage() : Canvas[Cell]

  override def toString : String = {
    (for (row <- canvas ) yield row.mkString(" ") ).mkString("\n")
  }

  def toNumericSequence() :Seq[Seq[Long]] = {

    def rowToSeqLong(row : Seq[Cell]) : List[Long] = {
      if (row.length>53) {
        val rows = row.splitAt(53)
        List(java.lang.Long.parseLong(rows._1.mkString,2)) ::: rowToSeqLong(rows._2)
      } else List(java.lang.Long.parseLong(row.mkString,2))
    }
    for (row<-canvas) yield {
      rowToSeqLong(row)
    }
  }

}

case class RandomCanvas(width: Int, height :Int) extends FiniteCanvas {
  def stage(): StringCanvas = {
    val allStagedCells = for (i <- 0 until canvas.length) yield (for (y <- 0 until canvas(i).length) yield getCell(i, y).stage(getNeighbors(i, y))).toSeq
    new StringCanvas(allStagedCells.toSeq)
  }

  val canvas: Seq[Seq[Cell]] = for (i <- 0 until width) yield  for (y <-0 until height) yield if (Random.nextBoolean()) LiveCell else DeadCell
}

case class StringCanvas(override val canvas: Seq[Seq[Cell]]) extends FiniteCanvas {
  def stage(): StringCanvas = {
    val allStagedCells = for (i <- 0 until canvas.length) yield (for (y <- 0 until canvas(i).length) yield getCell(i, y).stage(getNeighbors(i, y))).toSeq
    new StringCanvas(allStagedCells.toSeq)
  }
}

object StringCanvas {
  def apply(stringCanvas: String) = {
    val parsedRows = stringCanvas.filter(x => x.equals('\n') || x.isDigit).split('\n').filterNot(_.isEmpty)
    val arrays = for (row <- parsedRows) yield row.map {
      case '0' => DeadCell
      case '1' => LiveCell
      case _ => throw new IllegalArgumentException
    }.toSeq
    new StringCanvas(arrays)
  }
}