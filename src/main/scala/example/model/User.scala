package example.model

import java.util.UUID

import scala.collection.mutable.ArrayBuffer

final case class UserId(value: UUID)

final case class User(id: UserId, name: String, age: Int)
object User {
  def create(name: String, age: Int): User = User(UserId(UUID.randomUUID()), name, age)
}

object UserDataAccessor {

  def upsert(user: User): Unit = {
    val idx = db.indexWhere(_.id == user.id)
    if (idx == -1) {
      db += user
    } else {
      db(idx) = user
    }
  }

  // throwable
  def delete(userId: UserId): Unit = db.remove(db.indexWhere(_.id == userId))

  def findAll(): Seq[User] = db.toSeq

  private val db = ArrayBuffer.empty[User]
}

