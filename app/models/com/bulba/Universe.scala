package models.com.bulba

case class Universe[+S <: Seq[Cell], +T <: Seq[S]](layers: Seq[Canvas[S, T]])  {

  def stage(): Universe[S,T] = Universe(layers.map(_.stage()))

  def toNumericSequence: Seq[Seq[Seq[Long]]] = layers.par.map(_.toNumericSequence).seq
}


object RandomUniverse {
  def apply(layers: Int, width: Int, height: Int) : Universe[Seq[Cell], Seq[Seq[Cell]]] = {
    new Universe[Seq[Cell], Seq[Seq[Cell]]](for (i <- 0 until layers) yield RandomCanvas(width, height))
  }
}