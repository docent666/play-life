package models.com.bulba

import models.com.bulba.canvas.Canvas


class GameState(var canvas: Canvas[Seq[Cell], Seq[Seq[Cell]]]) {

  def advance() = {
    canvas = canvas.stage()
  }

  override def toString : String = {
    canvas.toString
  }

  def toNumericSequence : Seq[Seq[Long]] = {
    canvas.toNumericSequence
  }

  def toHex : Seq[String] = {
    canvas.toHex
  }

 }


