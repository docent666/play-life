package models.com.bulba

sealed trait Cell {
  def stage(neighbors : Seq[Cell]) : Cell
  def toNumber() :Int
}

case object LiveCell extends Cell {
  def stage(neighbors: Seq[Cell]): Cell = {
    neighbors.count(_.equals(this)) match {
      case x if 2 to 3 contains x => this
      case _ => DeadCell
    }
  }

  override def toString : String =  "0"
  override def toNumber : Int = 0

}

case object DeadCell extends Cell {
  def stage(neighbors: Seq[Cell]): Cell = {
    if (neighbors.count(_.equals(LiveCell)) == 3) LiveCell else this
  }

  override def toString : String =  "1"
  override def toNumber : Int = 1
}

