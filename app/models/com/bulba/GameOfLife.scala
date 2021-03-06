package models.com.bulba

import models.com.bulba.canvas.{StringCanvas, RandomCanvas}

object GameOfLife extends App {

  val demoCanvas = StringCanvas(
    """000010
       010110
       011110
       101010
       110011
       010001
    """.stripMargin)
  val con = new tools.jline.console.ConsoleReader()
  //var state = new GameState(StringCanvas(demoCanvas))
  val state = new GameState(new RandomCanvas(1000,1000))
  while (true) {
    con.readVirtualKey()
    println(state)
    state.advance()
  }
}