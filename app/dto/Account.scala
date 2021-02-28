package dto

import play.api.libs.json.{Json, OWrites}

case class Account(userId: Int, isActive: Boolean, id: Int = 0)

object Account {
    implicit val writes: OWrites[Account] = Json.writes[Account]

    def tupled = (Account.apply _).tupled
}
