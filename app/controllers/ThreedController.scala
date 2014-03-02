package controllers

import play.api.mvc._
import play.api.libs.json.Json
import play.api.Routes
import models.com.bulba._
import collection.JavaConverters._
import com.google.common.cache.CacheBuilder
import java.util.concurrent.TimeUnit
import scala.Some
import play.api.mvc.SimpleResult
import scala.Some
import play.api.mvc.SimpleResult
import play.mvc.Result

object ThreedController extends Controller {

  val states = CacheBuilder.
    newBuilder().
    expireAfterAccess(1, TimeUnit.HOURS).
    build[String, Game3DState]().asMap().asScala

  def index = Action {
    Ok(views.html.threed.render())
  }

  def getState = Action {
    implicit request =>
      session.get("state") match {

        case Some(sessionState) =>
          if (!states.contains(sessionState.asInstanceOf[String])) {
            resetHelper(session.get("layers").getOrElse(20).asInstanceOf[Int], session.get("height").getOrElse(300).asInstanceOf[Int], session.get("width").getOrElse(424).asInstanceOf[Int])
          } else {
            val state = states.get(sessionState.asInstanceOf[String]).get
            states += (sessionState -> state)
            state.advance()
            Ok(Json.toJson(state.toNumericSequence))
              .withSession("state" -> sessionState)
          }

        case None =>
          throw new Exception("not initialized")
      }

  }


  def resetHelper(layers: Int, height: Int, width: Int)(implicit request: Request[AnyContent]): SimpleResult = {
    session.get("state") match {

      case Some(sessionState) =>
        states += (sessionState -> new Game3DState(Universe(layers, width, height)))
        Ok(Json.toJson(states(sessionState).toNumericSequence))
          .withSession("state" -> sessionState, "height" -> height.toString, "width" -> width.toString)

      case None =>
        val state = new Game3DState(Universe(layers, width, height))
        states += (state.hashCode().toString -> state)
        Ok(Json.toJson(state.toNumericSequence))
          .withSession("state" -> state.hashCode().toString)
    }
  }

  def reset(layers: Int, height: Int, width: Int) = Action {
      implicit request =>
        resetHelper(layers, height, width)
 }

}