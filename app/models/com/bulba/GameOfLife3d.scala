package models.com.bulba

object GameOfLife3d extends App {


  val con = new tools.jline.console.ConsoleReader()
  val state = new Game3DState(Universe(3, 5, 5))
  while (true) {
    con.readVirtualKey()
    println(state)
    state.advance()
  }
}