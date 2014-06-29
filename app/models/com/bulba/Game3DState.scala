package models.com.bulba

class Game3DState(var universe: Universe[Seq[Cell], Seq[Seq[Cell]]]) {

  def advance() = {
    universe = universe.stage()
  }

  override def toString : String = {
    universe.toString
  }

  def toNumericSequence : Seq[Seq[Seq[Long]]] = {
    universe.toNumericSequence
  }

  def toHex: Seq[Seq[String]] = universe.toHex

 }


