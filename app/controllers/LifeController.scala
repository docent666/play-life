package controllers

import play.api.mvc._
import play.api.libs.json.Json
import play.api.Routes
import models.com.bulba.GameState
import collection.JavaConverters._
import com.google.common.cache.CacheBuilder
import java.util.concurrent.TimeUnit
import scala.Some
import play.api.mvc.SimpleResult
import models.com.bulba.canvas.RandomCanvas

object LifeController extends Controller {

  val states = CacheBuilder.
    newBuilder().
    expireAfterAccess(1, TimeUnit.HOURS).
    build[String, GameState]().
    asMap().
    asScala

  def getState = Action {
    implicit request =>
      session.get("state") match {

        case Some(sessionState) =>
          if (!states.contains(sessionState.asInstanceOf[String])) {
            resetHelper(session.get("height").getOrElse(300).asInstanceOf[Int], session.get("width").getOrElse(424).asInstanceOf[Int])
          } else {
            val state = states(sessionState.asInstanceOf[String])
            states += (sessionState -> state)
            state.advance()
            Ok(Json.toJson(state.toHex))
              .withSession("state" -> sessionState)
          }

        case None =>
          throw new Exception("not initialized")
      }

  }


  def resetHelper(height: Int, width: Int)(implicit request: Request[AnyContent]): SimpleResult = {
    session.get("state") match {

      case Some(sessionState) =>
        states += (sessionState -> new GameState(RandomCanvas(height, width)))
        Ok(Json.toJson(states(sessionState).toHex))
          .withSession("state" -> sessionState, "height" -> height.toString, "width" -> width.toString)

      case None =>
        val state = new GameState(RandomCanvas(height, width))
        states += (state.hashCode().toString -> state)
        Ok(Json.toJson(state.toHex))
          .withSession("state" -> state.hashCode().toString)
    }
  }

  def reset(height: Int, width: Int) = Action {
      implicit request =>
        resetHelper(height, width)
 }


  def javascriptRoutes = Action {
    implicit request =>
      Ok(Routes.javascriptRouter("jsRoutes")
        (routes.javascript.LifeController.getState,
         routes.javascript.LifeController.reset,
         routes.javascript.ThreedController.getState,
         routes.javascript.ThreedController.reset
          )).as(JAVASCRIPT)
  }

}