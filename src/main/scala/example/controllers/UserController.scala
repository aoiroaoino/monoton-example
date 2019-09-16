package example.controllers

import java.util.UUID

import example.model.{User, UserDataAccessor, UserId}
import io.circe.{Json, Printer}
import monoton.http.codec.CirceJson
import monoton.http.{Form, FormMapping}
import monoton.server.{Controller, Handler}

class UserController extends Controller {
  import UserController._
  import monoton.http.codec.circe._

  def list(): RequestHandler =
    Handler.later(UserDataAccessor.findAll()).map(users => Ok(users.mkString("\n")))

  def create(): RequestHandler =
    for {
      form <- request.body.as(createUserFormMapping)
      user <- Handler.later(User.create(form.name, form.age))
      _    <- Handler.later(UserDataAccessor.upsert(user))
    } yield Ok("OK!")

  def delete(userId: UUID): RequestHandler =
    for {
      _ <- Handler.catchNonFatal(UserDataAccessor.delete(UserId(userId)))(
        _ => NotFound(s"User(id: $userId) is not found")
      )
    } yield Ok(s"User(id: $userId) was deleted")

  def update(userId: UserId): RequestHandler =
    for {
      json   <- request.body.as(CirceJson)
      _       = println(s"req: " + json.pretty(Printer.noSpaces))
      resJson = UpdateResult.create(userId, "Update OK!").toJson
    } yield Ok(resJson)
}

object UserController {

  final case class CreateUserForm(name: String, age: Int)
  val createUserFormMapping: FormMapping[CreateUserForm] =
    Form.mapping("name", "age")(CreateUserForm.apply)

  final case class UpdateResult(userId: UUID, msg: String) {
    import io.circe.syntax._
    import io.circe.generic.auto._
    def toJson: Json = this.asJson
  }
  object UpdateResult {
    def create(userId: UserId, msg: String): UpdateResult =
      UpdateResult(userId.value, msg)
  }
}
