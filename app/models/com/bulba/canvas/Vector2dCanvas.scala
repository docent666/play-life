package models.com.bulba.canvas

import models.com.bulba.{VC, VVC}

case class Vector2dCanvas(override val canvas: VVC, override val changedCells: Set[(Int, Int)])
  extends VectorCanvas with Finite2dCanvas[VC, VVC] {
  def stage(): Vector2dCanvas = {
    val staged = stagedCells
    val changed = for (x <- staged.indices; y <- staged(x).indices; if canvas(x)(y) != staged(x)(y)) yield (x, y)
    Vector2dCanvas(staged, changed.toSet)
  }

  override def haveNeighborsChanged(x: Int, y: Int): Boolean = {
    for {i1 <- x - 1 to x + 1; y1 <- y - 1 to y + 1} if (changedCells.contains((i1, y1))) return true
    false
  }
}