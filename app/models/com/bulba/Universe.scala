package models.com.bulba

class Universe[S <: Seq[Cell], T <: Seq[S]](layers:  => Layers[S, T])  {

  implicit val universe  = this

  def stage(): Universe[S,T] = new Universe(layers.stageStatefully())

  def toNumericSequence: Seq[Seq[Seq[Long]]] = layers.par.map(_.toNumericSequence).seq

  override def toString : String = layers.toString

}

object Universe {
  def apply[S <: Seq[Cell], T <: Seq[S]](layersInt: Int, width: Int, height: Int) : Universe[S, T] = {
    lazy val lay: Layers[S, T] = new Layers[S, T](for (i <- 0 until layersInt) yield new Random3dCanvas(width, height, i, lay))
    new Universe(lay)
  }
}


class Layers[S <: Seq[Cell], T <: Seq[S]](var layers: Seq[Canvas[S, T]]) extends Seq[Canvas[S, T]] {
  val dead = new DeadCanvas

  def below(index : Int) : Canvas[S, T] = if (index-1<layers.length && index-1>=0) layers(index-1) else dead

  def above(index : Int) : Canvas[S, T] = if (index+1<layers.length && index+1>=0) layers(index+1) else dead

  override def apply(idx: Int): Canvas[S, T] = layers(idx)

  override def length: Int = layers.length

  override def iterator: Iterator[Canvas[S, T]] = layers.iterator

  override def toString : String = layers map (_.toString) mkString "\n"

  def stageStatefully() : Layers[S, T] = {
    layers = layers.map(_.stage())
    this
  }
}