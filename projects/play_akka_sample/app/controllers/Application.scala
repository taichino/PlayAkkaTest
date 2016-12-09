package controllers

import akka.actor._
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.Future
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import play.api._
import play.api.mvc._
import play.api.libs.ws._
import play.api.libs.json._
import com.typesafe.config.ConfigFactory


class Application extends Controller {

  val actorSystem = ActorSystem.create("master", ConfigFactory.load().getConfig("master"))
  val remoteActor = actorSystem.actorSelection("akka.tcp://GithubActorSystem@127.0.0.1:5151/user/GithubActor")
  implicit val timeout: Timeout = 5 seconds

  def let_actor_work(repos: String) = Action.async {
    (remoteActor ? repos).mapTo[JsObject].map { message =>
      Ok(message)
    }
  }
}
