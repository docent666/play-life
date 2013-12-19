package models.com.bulba

import scala.collection.parallel.ParSeq

class GameState(val canvas: Canvas[Seq[Cell], Seq[Seq[Cell]]]) {

  def advance() : GameState = new GameState(canvas.stage())

  override def toString() : String = {
    canvas.toString
  }

  def toNumericSequence() : Seq[Seq[Long]] = {
    canvas.toNumericSequence
  }

 }


