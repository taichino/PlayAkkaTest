package controllers

import play.api._
import play.api.mvc._

class Application extends Controller {
  def hello = Action {
    Ok("Hello Play World")
  }
}
