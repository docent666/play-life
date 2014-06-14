package models.com.bulba.canvas

import models.com.bulba.Cell

case class Vector2dCanvas[S <: Seq[Cell], T <: Seq[S]](override val canvas: T, override val changedCells: Set[(Int, Int)]) extends VectorCanvas[S, T] with Finite2dCanvas[S, T] {
  def stage(): Vector2dCanvas[S, T] = {
    val staged = stagedCells
    val changed = for (x <- 0 until staged.length; y <- 0 until staged(x).length; if canvas(x)(y) != staged(x)(y)) yield (x, y)
    new Vector2dCanvas[S, T](staged.map(_.toSeq).toSeq.asInstanceOf[T], changed.toSet)
  }

  override def haveNeighborsChanged(x: Int, y: Int): Boolean = {
    for {i1 <- x - 1 to x + 1; y1 <- y - 1 to y + 1} if (changedCells.contains((i1, y1))) return true
    false
  }
}