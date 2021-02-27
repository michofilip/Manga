package dto

import play.api.libs.json.{Json, OWrites}

case class User(login: String, id: Int = 0)

object User {
    implicit val writes: OWrites[User] = Json.writes[User]

    def tupled = (User.apply _).tupled
}
