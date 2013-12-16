package models.com.bulba

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers


class PopulatorSpec extends FlatSpec with ShouldMatchers {
    ignore  should "create a finite canvas" in {
      val canvas  = new RandomPopulator().populate()
    }
}
