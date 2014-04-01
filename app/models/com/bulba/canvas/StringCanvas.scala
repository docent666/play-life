package models.com.bulba.canvas

import models.com.bulba._
import models.com.bulba.stagingstrategy.Life2dStagingStrategy

case class StringCanvas(override val canvas: Seq[Seq[Cell]]) extends Finite2dCanvas[Seq[Cell], Seq[Seq[Cell]]] {
  def stage(): StringCanvas = {
    val allStagedCells = for (i <- 0 until canvas.length)
    yield (for (y <- 0 until canvas(i).length) yield getCell(i, y).stage(getNeighbors(i, y), Life2dStagingStrategy)).toSeq
    new StringCanvas(allStagedCells.toSeq)
  }

  override def haveNeighborsChanged(x: Int, y: Int): Boolean = true

  override val changedCells: Set[(Int, Int)] = (for (x <- 0 until canvas.length;y <- 0 until canvas(x).length) yield (x,y)).toSet
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