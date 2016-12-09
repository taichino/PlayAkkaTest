//
// Application.scala
//

package controllers

import javax.inject._

import scala.concurrent.Future
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

import play.api._
import play.api.mvc._
import play.api.libs.ws._
import play.api.libs.json._


class Application @Inject() (ws: WSClient) extends Controller {

  def let_play_work(repos: String) = Action.async {
    val reqs = repos.split(",").map { repo =>
      ws.url(f"https://api.github.com/repos/iheartradio/$repo/languages").withHeaders(
        "User-Agent" -> "demoapp",
        "Authorization" -> "token bc030cfc8d83f80bc0316912928840a84328f3d4",
        "Content-Type" -> "application/json"
      ).get
    }.toList

    Future.sequence(reqs).map { responses =>
      val total = collection.mutable.Map[String, Int]().withDefaultValue(0)
      responses.map { response =>
        Json.parse(response.body) match {
          case o: JsObject => o.keys.map {
            key => total.update(key, total(key) + (o \ key).as[Int])
          }
          case _ => println("@@ Something is wrong! @@")
        }
      }
      Ok(Json.toJson(total))
    }
  }
}
