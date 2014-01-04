package controllers

import play.api.mvc.{Action, Controller}
import play.api.libs.json.Json
import play.api.Routes
import models.com.bulba.{RandomCanvas, GameState}
import collection.JavaConverters._
import com.google.common.cache.CacheBuilder
import java.util.concurrent.TimeUnit

object LifeController extends Controller {

  val states = CacheBuilder.
    newBuilder().
    expireAfterAccess(1, TimeUnit.SECONDS).
    build[String,GameState]().
    asMap().
    asScala


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

  def reset = {
    Action {
      implicit request =>
        session.get("state") match {

          case Some(sessionState) =>
            states += (sessionState -> new GameState(RandomCanvas(300, 424)))
            Ok(Json.toJson(states(sessionState).toNumericSequence()))
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
      Ok(Routes.javascriptRouter("jsRoutes")
        (routes.javascript.LifeController.getState,
        routes.javascript.LifeController.reset)).as(JAVASCRIPT)
  }

}