import controllers.routes
import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import play.api.test.WithApplication
import play.mvc.Http.Status._
import play.mvc.Result
import play.test.Helpers._

class MainControllerSpec extends FlatSpec with ShouldMatchers{

  "Index" should "contain a correct string" in new WithApplication {
        val result: Result = callAction(routes.ref.MainController.index)
        status(result) should be (OK)
        contentType(result) should be ("text/html")
        charset(result) should be ("utf-8")
        contentAsString(result) should include ("Auto Refresh")
  }

}
