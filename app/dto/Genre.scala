package dto

import play.api.libs.json.{Json, OWrites}

case class Genre(name: String, id: Int = 0)

object Genre {
    implicit val writes: OWrites[Genre] = Json.writes[Genre]

    def tupled = (Genre.apply _).tupled
}
