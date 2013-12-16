import controllers.routes;
import org.junit.*;

import play.mvc.*;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;


public class MainControllerTest {



    @Test
    public void indexShouldContainTheCorrectString() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Result result = callAction(routes.ref.MainController.index());
                assertThat(status(result)).isEqualTo(OK);
                assertThat(contentType(result)).isEqualTo("text/html");
                assertThat(charset(result)).isEqualTo("utf-8");
                assertThat(contentAsString(result)).contains("Get next stage");
            }
        });
    }

}
