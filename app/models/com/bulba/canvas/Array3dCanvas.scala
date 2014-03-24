package models.com.bulba.canvas

import models.com.bulba._

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