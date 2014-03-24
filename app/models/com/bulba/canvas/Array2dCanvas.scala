package models.com.bulba.canvas

import models.com.bulba.Cell

case class Array2dCanvas[S <: Seq[Cell], T <: Seq[S]](override val canvas: T) extends ArrayCanvas[S, T] with Finite2dCanvas[S, T] {
  def stage(): Array2dCanvas[S, T] = {
    new Array2dCanvas[S, T](stagedCells.map(_.toSeq).toSeq.asInstanceOf[T])
  }
}