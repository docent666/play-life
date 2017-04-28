package models.com.bulba.canvas

import models.com.bulba._

class Vector3dCanvas[S <: Seq[Cell], T <: Seq[S]](override val canvas: T, index: Int, layers: => Layers[S, T], override val changedCells: Set[(Int, Int)]) extends VectorCanvas[S, T] with Finite3dCanvas[S, T] {


  override def canvasBelow: Canvas[S, T] = {
    layers.below(index)
  }

  override def canvasAbove: Canvas[S, T] = {
    layers.above(index)
  }

  def stage(): Canvas[S, T] = {
    val staged = stagedCells
    val changed = for (x <- staged.indices; y <- staged(x).indices; if canvas(x)(y) != staged(x)(y)) yield (x, y)
    new Vector3dCanvas[S, T](staged.map(_.toSeq).asInstanceOf[T], index, layers, changed.toSet)
  }

  override def haveNeighborsChanged(x: Int, y: Int): Boolean = {
    haveNeighborsChangedInCanvas(x,y,canvasBelow) ||
      haveNeighborsChangedInCanvas(x,y,canvasAbove) ||
        haveNeighborsChangedInCanvas(x,y,this)
  }

  private def haveNeighborsChangedInCanvas(x: Int, y: Int, c : Canvas[S, T]) : Boolean =  {
    for {i1 <- x - 1 to x + 1; y1 <- y - 1 to y + 1} if (c.changedCells.contains((i1, y1))) return true
    false
  }

}