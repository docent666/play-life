package models.com.bulba.canvas

import scala.util.Random
import models.com.bulba._

class Random3dCanvas[S <: Seq[Cell], T <: Seq[S]](width: Int, height: Int, index: Int, layers: => Layers[S, T]) extends Vector3dCanvas[S, T](Seq.empty[S].asInstanceOf[T], index, layers, (for (x <- 0 until width; y<-0 until height) yield (x, y)).toSet) {


  override def stage(): Canvas[S, T] = {
    new Vector3dCanvas[S, T](canvas.asInstanceOf[T], index, layers, changedCells)
  }

  override val canvas = (for (i <- 0 until width) yield
    for (y <- 0 until height) yield
      if (Random.nextInt(10) > 6) LiveCell else DeadCell).asInstanceOf[T]

  override def haveNeighborsChanged(x: Int, y: Int): Boolean = true

  override val changedCells: Set[(Int, Int)] = (for (x <- 0 until width; y<-0 until height) yield (x, y)).toSet
}