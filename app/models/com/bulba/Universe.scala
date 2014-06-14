package models.com.bulba

import models.com.bulba.canvas.Random3dCanvas

class Universe[S <: Seq[Cell], T <: Seq[S]](layers: Layers[S, T])  {

  def stage(): Universe[S,T] = {
    new Universe(layers.stageStatefully())
  }

  def toNumericSequence: Seq[Seq[Seq[Long]]] = layers.par.map(_.toNumericSequence).seq

  override def toString : String = layers.toString()

}

object Universe {
  def apply[S <: Seq[Cell], T <: Seq[S]](layersInt: Int, width: Int, height: Int) : Universe[S, T] = {
    lazy val lay: Layers[S, T] = new Layers[S, T](for (i <- 0 until layersInt) yield new Random3dCanvas(width, height, i, lay))
    new Universe(lay)
  }
}


