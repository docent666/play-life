package models.com.bulba.canvas

import scala.util.Random
import models.com.bulba._

case class RandomCanvas[S <: Seq[Cell], T <: Seq[S]](width: Int, height: Int) extends Finite2dCanvas[S, T] {
  def stage(): Canvas[S, T] = {
    new Array2dCanvas[S, T](canvas.asInstanceOf[T])
  }

  val canvas = (for (i <- 0 until width) yield
    for (y <- 0 until height) yield
      if (Random.nextInt(10) > 8) LiveCell else DeadCell).asInstanceOf[T]
}
