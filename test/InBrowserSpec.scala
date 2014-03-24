import org.fest.assertions.Assertions._
import org.scalatest.WordSpec
import play.libs.F
import play.test.Helpers._
import play.test.TestBrowser


//kind of unrelated to anything else but keeping it for reference
class InBrowserSpec extends WordSpec {

  "Browser" should {
    "connect to server page"  in  {
      running(testServer(3333, fakeApplication(inMemoryDatabase)), HTMLUNIT, new F.Callback[TestBrowser] {
        def invoke(browser: TestBrowser) {
          browser.goTo("http://localhost:3333")
          assertThat(browser.pageSource).contains("Auto Refresh")
        }
      })

    }
  }

}
