package models.com.bulba.canvas

import models.com.bulba.Cell
import models.com.bulba.stagingstrategy.StagingStrategy

trait Canvas[+S <: Seq[Cell], +T <: Seq[S]] {

  val canvas: T

  protected implicit val strategy: StagingStrategy

  def getCell(x: Int, y: Int): Cell

  def getNeighbors(x: Int, y: Int): S

  override def toString: String = {
    canvas map (_.mkString("")) mkString "\n"
  }

  def toNumericSequence: Seq[Seq[Long]] = {

    def rowToSeqLong(row: Seq[Cell]): Seq[Long] = {
      if (row.length > 53) {
        val rows = row.splitAt(53)
        Seq(java.lang.Long.parseLong(rows._1.mkString, 2)) ++ rowToSeqLong(rows._2)
      } else Seq(java.lang.Long.parseLong(row.mkString, 2))
    }
    canvas.par.map(rowToSeqLong(_)).seq
  }

  def stage(): Canvas[S, T]

}







