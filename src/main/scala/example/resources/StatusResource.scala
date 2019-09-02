package example.resources

import monoton.http.Response
import monoton.server.{Handler, Resource}

class StatusResource extends Resource {

  def ping: Handler[Response] = Handler.pure(Ok("pong"))
}
