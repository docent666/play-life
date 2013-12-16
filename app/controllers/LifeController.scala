package controllers

import play.api.mvc.{Action, Controller}
import play.api.libs.json.Json
import play.api.Routes
import models.com.bulba.{RandomCanvas, GameState}

object LifeController extends Controller {

  implicit val fooWrites = Json.writes[Message]

  var state = new GameState(RandomCanvas(300,50))

  def getState = Action {
    state=state.advance()
    Ok(Json.toJson(state.toNumericSequence()))

  }

  def javascriptRoutes = Action { implicit request =>
    Ok(Routes.javascriptRouter("jsRoutes")(routes.javascript.LifeController.getState)).as(JAVASCRIPT)
  }

}