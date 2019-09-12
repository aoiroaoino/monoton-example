package example

import java.util.UUID

import example.controllers.{StatusController, UserController}
import example.model.UserId
import javax.inject.{Inject, Singleton}
import monoton.server.RoutingDSL
import monoton.util.Read

@Singleton
class MyRouter @Inject()(
  statusResource: StatusController,
  userResource: UserController
) extends RoutingDSL {

  implicit def userIdRead(implicit M: Read[UUID]): Read[UserId] = M.map(UserId.apply)

  // status
  GET ~ "/ping" to statusResource.ping

  // users
  resource(userResource) { user =>
    GET    ~ "/users"      to user.list
    POST   ~ "/users"      to user.create
    PUT    ~ "/users/{id}" to user.update _
    DELETE ~ "/users/{id}" to user.delete _
  }
}
