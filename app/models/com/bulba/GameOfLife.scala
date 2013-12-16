package models.com.bulba

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
  var state = new GameState(RandomCanvas(100,100))
  while (true) {
    con.readVirtualKey()
    println(state)
    state = state.advance()
  }
}