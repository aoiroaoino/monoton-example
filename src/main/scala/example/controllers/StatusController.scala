package example.controllers

import monoton.http.Response
import monoton.server.{Controller, Handler}

class StatusController extends Controller {
  def ping: Handler[Response] = Handler.pure(Ok("pong"))
}
