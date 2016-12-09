package remote

import akka.actor._

object AngryDeveloper extends App  {
  val system = ActorSystem("RemoteSystem")
  system.actorOf(Props[AngryDeveloperActor], name = "AngryDeveloperActor")
}

class AngryDeveloperActor extends Actor {

  var counter = 0
  println("I'm working :)")

  def receive = {
    case msg: String =>
      if (counter < 5) {
        println(f"My manager is saying: '$msg'")
        counter += 1
        sender ! f"This is the $counter th time I got interrupted!"
      }
      else {
        println("Lets ignore the f**king manager")
      }
  }
}
