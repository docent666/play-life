package controllers

import play.api.mvc.{Action, Controller}
import play.api.libs.json.Json
import play.api.Routes
import models.com.bulba.{RandomCanvas, GameState}

object LifeController extends Controller {

  var states = Map[String, GameState]()

  def getState = {
    Action {
      implicit request =>
        session.get("state") match {

          case Some(sessionState) =>
            val state = states.getOrElse(sessionState.asInstanceOf[String], new GameState(RandomCanvas(300, 424)))
            if (!states.contains(sessionState.asInstanceOf[String]))
              states += (sessionState -> state)
            state.advance()
            Ok(Json.toJson(state.toNumericSequence()))
              .withSession("state" -> sessionState)

          case None =>
            val state = new GameState(RandomCanvas(300, 424))
            states += (state.hashCode().toString -> state)
            Ok(Json.toJson(state.toNumericSequence()))
              .withSession("state" -> state.hashCode().toString)
        }
    }

  }

  def javascriptRoutes = Action {
    implicit request =>
      Ok(Routes.javascriptRouter("jsRoutes")(routes.javascript.LifeController.getState)).as(JAVASCRIPT)
  }

}