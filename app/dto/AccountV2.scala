package dto

import play.api.libs.json.{Json, OWrites}

// TODO rename to Account
case class AccountV2(id: Int, isActive: Boolean, user: User)

object AccountV2 {
    implicit val writes: OWrites[AccountV2] = Json.writes[AccountV2]
}
