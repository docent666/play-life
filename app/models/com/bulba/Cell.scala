package models.com.bulba

sealed trait Cell {
  def stage(neighbors : Seq[Cell],strategy: StagingStrategy) : Cell
  def toNumber :Int
}

case object LiveCell extends Cell {
  def stage(neighbors: Seq[Cell], strategy: StagingStrategy): Cell = {
    strategy.stage(this, neighbors)
  }

  override def toString : String =  "0"
  override def toNumber : Int = 0

}

case object DeadCell extends Cell {
  def stage(neighbors: Seq[Cell], strategy: StagingStrategy): Cell = {
    strategy.stage(this, neighbors)
  }

  override def toString : String =  "1"
  override def toNumber : Int = 1
}

trait StagingStrategy {
  def stage(cell: Cell, neighbors: Seq[Cell]) : Cell
}

object Life2dStagingStrategy  extends StagingStrategy{
  def stage(cell: Cell, neighbors: Seq[Cell]) : Cell = {
    (cell, neighbors.count(_.equals(LiveCell))) match {
      case (DeadCell, x) if x==3 => LiveCell
      case (DeadCell, _) => DeadCell
      case (LiveCell, x) if 2 to 3 contains x => LiveCell
      case (LiveCell, _) => DeadCell
    }
  }
}

//  B9 10 11/S 6 7 8 9 10 11
object Life3dStagingStrategy extends StagingStrategy{
  def stage(cell: Cell, neighbors: Seq[Cell]) : Cell = {
    (cell, neighbors.count(_.equals(LiveCell))) match {
      case (DeadCell, x) if 9 to 11 contains x => LiveCell
      case (DeadCell, _) => DeadCell
      case (LiveCell, x) if 6 to 11 contains x => LiveCell
      case (LiveCell, _) => DeadCell
    }
  }
}

