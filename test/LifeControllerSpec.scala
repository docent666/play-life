package test

import play.api.test._
import play.api.test.Helpers._
import org.scalatest.WordSpec
import org.scalatest.matchers.ShouldMatchers


class LifeControllerSpec extends WordSpec with ShouldMatchers{
  
  "LifeController" should {
    
    "getState should return JSON" in new WithApplication {
      val result = controllers.LifeController.getState(FakeRequest().withSession("state" -> "dummy"))

      status(result) shouldEqual OK
      contentType(result) should be(Some("application/json"))
      charset(result) should be (Some("utf-8"))
      contentAsString(result) should startWith("[")
    }

  }
}