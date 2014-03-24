package models.com.bulba.stagingstrategy

import models.com.bulba.{LiveCell, DeadCell, Cell}

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
