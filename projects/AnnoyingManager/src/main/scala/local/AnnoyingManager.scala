package local

import akka.actor._

object AnnoyingManager extends App {

  implicit val system = ActorSystem("LocalSystem")
  val mgrActor = system.actorOf(Props[AnnoyingManagerActor], name = "AnnoyingManagerActor")
  mgrActor ! "START"
}

class AnnoyingManagerActor extends Actor {

  val mydev = context.actorSelection("akka.tcp://RemoteSystem@127.0.0.1:5150/user/AngryDeveloperActor")

  def receive = {
    case "START" =>
      println("I'm going to check my devs status pretty hard :)")
      mydev ! "How's going"

    case msg: String =>
      println(s"My dev is saying: '$msg'")
      mydev ! "So whats your status :)"
  }
}
