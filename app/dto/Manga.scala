package dto

import play.api.libs.json.{Json, OWrites}

case class Manga(title: String, id: Int = 0)

object Manga {
    implicit val writes: OWrites[Manga] = Json.writes[Manga]

    def tupled = (Manga.apply _).tupled
}
