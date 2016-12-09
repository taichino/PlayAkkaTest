package github

import scala.concurrent._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import akka.actor._
import akka.stream.ActorMaterializer
import play.api.libs.json.{JsObject, Json}
import play.api.libs.ws.ahc.AhcWSClient

object GithubActorApp extends App  {
  val system = ActorSystem("GithubActorSystem")
  system.actorOf(Props[GithubActor], name = "GithubActor")
}

class GithubActor extends Actor {
  implicit val materializer = ActorMaterializer()
  val wsClient = AhcWSClient()

  def receive = {
    case repos: String =>
      val s = sender

      val reqs = repos.split(",").map { repo =>
        wsClient.url(f"https://api.github.com/repos/iheartradio/$repo/languages").withHeaders(
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
        s ! Json.toJson(total)
      }
  }
}
