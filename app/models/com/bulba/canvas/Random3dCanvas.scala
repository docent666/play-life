package models.com.bulba.canvas

import scala.util.Random
import models.com.bulba._

class Random3dCanvas[S <: Seq[Cell], T <: Seq[S]](width: Int, height: Int, index: Int, layers: => Layers[S, T]) extends Array3dCanvas[S, T](Seq.empty[S].asInstanceOf[T], index, layers) {


  override def stage(): Canvas[S, T] = {
    new Array3dCanvas[S, T](canvas.asInstanceOf[T], index, layers)
  }

  override val canvas = (for (i <- 0 until width) yield
    for (y <- 0 until height) yield
      if (Random.nextInt(10) > 6) LiveCell else DeadCell).asInstanceOf[T]
}