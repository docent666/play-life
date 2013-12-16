package models.com.bulba

class GameState(val canvas: Canvas[Cell]) {

  def advance() : GameState = new GameState(canvas.stage())

  override def toString() : String = {
    canvas.toString
  }

  def toNumericSequence() : Seq[Seq[Long]] = {
    canvas.toNumericSequence
  }

 }


