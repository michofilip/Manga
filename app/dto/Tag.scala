package dto

import play.api.libs.json.{Json, OWrites}

case class Tag(accountId: Int, tag: String, id: Int = 0)

object Tag {
    implicit val writes: OWrites[Tag] = Json.writes[Tag]
}
