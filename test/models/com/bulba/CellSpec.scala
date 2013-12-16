package models.com.bulba

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers


class CellSpec extends FlatSpec with ShouldMatchers{

  "Any live cell"  should "die if with fewer than two live neighbors" in {
    val withOneLiveNeighbor = neighborsWithLiveCells(1)
    LiveCell.stage(withOneLiveNeighbor) should be (DeadCell)
    val withNoLiveNeighbors = neighborsWithLiveCells(0)
    LiveCell.stage(withNoLiveNeighbors) should be (DeadCell)
  }
  it should "die if more than three live neighbors" in {
    val withFourLiveNeighbors = neighborsWithLiveCells(4)
    LiveCell.stage(withFourLiveNeighbors) should be (DeadCell)
  }
  it should "live if two or three neighbors" in {
    val withTwoLiveNeighbors = neighborsWithLiveCells(2)
    LiveCell.stage(withTwoLiveNeighbors) should be (LiveCell)
    val withThreeLiveNeighbors = neighborsWithLiveCells(3)
    LiveCell.stage(withThreeLiveNeighbors) should be (LiveCell)
  }
  "Any dead cell" should "come to life if three live neighbors" in {
    val withThreeLiveNeighbors = neighborsWithLiveCells(3)
    DeadCell.stage(withThreeLiveNeighbors) should be (LiveCell)
  }
  it should "remain dead otherwise" in {
    val withTwoLiveNeighbors = neighborsWithLiveCells(2)
    DeadCell.stage(withTwoLiveNeighbors) should be (DeadCell)
    val withOneLiveNeighbor = neighborsWithLiveCells(1)
    DeadCell.stage(withOneLiveNeighbor) should be (DeadCell)
    val withFourLiveNeighbors = neighborsWithLiveCells(4)
    DeadCell.stage(withFourLiveNeighbors) should be (DeadCell)
  }


  def neighborsWithLiveCells(count : Int) : Seq[Cell] = {
    val liveCells : Seq[Cell] = for(x <- 0 until count) yield LiveCell
    val deadCells : Seq[Cell] = for(x <- 0 until (8-count)) yield DeadCell
    liveCells ++ deadCells
  }

}
