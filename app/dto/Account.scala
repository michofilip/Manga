package dto

import play.api.libs.json.{Json, OWrites}

case class Account(id: Int, isActive: Boolean, user: User)

object Account {
    implicit val writes: OWrites[Account] = Json.writes[Account]
}
