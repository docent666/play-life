package models.com.bulba.canvas

import models.com.bulba.Cell

case class Array2dCanvas[S <: Seq[Cell], T <: Seq[S]](override val canvas: T, override val changedCells: Set[(Int, Int)]) extends ArrayCanvas[S, T] with Finite2dCanvas[S, T] {
  def stage(): Array2dCanvas[S, T] = {
    val staged: Array[Array[Cell]] = stagedCells
    val changed = for (x <- 0 until staged.length; y <- 0 until staged(x).length; if canvas(x)(y) != staged(x)(y)) yield (x, y)
    new Array2dCanvas[S, T](staged.map(_.toSeq).toSeq.asInstanceOf[T], changed.toSet)
  }

  override def haveNeighborsChanged(x: Int, y: Int): Boolean = {
    for {i1 <- x - 1 to x + 1; y1 <- y - 1 to y + 1} if (changedCells.contains((i1, y1))) return true
    false
  }
}