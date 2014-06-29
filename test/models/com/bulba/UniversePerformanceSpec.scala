package models.com.bulba

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

class UniversePerformanceSpec extends FlatSpec with ShouldMatchers{

  "universe" should "stage in 10 seconds" in {
    val universes = for (i <- 0 until 10) yield new Game3DState(Universe(10,50,50))
    for (universe <- universes) universe.advance()
    val start = System.currentTimeMillis()
    for (i <- 0 until 10; universe <-universes) universe.advance()
    val stop = System.currentTimeMillis()
    (stop - start)/1000 should be < 10L
  }

  "universe" should "display in 500ms" in {
    val universes = for (i <- 0 until 10) yield new Game3DState(Universe(10,50,50))
    for (universe <- universes) universe.advance()
    val start = System.currentTimeMillis()
    for (i <- 0 until 10; universe <- universes) universe.toNumericSequence
    val stop = System.currentTimeMillis()
    (stop - start) should be < 500L
  }

  "universe" should "convert to binary representation in 400ms" in {
    val universes = for (i <- 0 until 10) yield new Game3DState(Universe(10,50,50))
    for (universe <- universes) universe.advance()
    val start = System.currentTimeMillis()
    for (i <- 0 until 10; universe <- universes) universe.toHex
    val stop = System.currentTimeMillis()
    (stop - start) should be < 400L
  }

}
