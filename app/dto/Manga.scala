package dto

import play.api.libs.json.{Json, OWrites}

case class Manga(id: Int, title: String, franchises: Seq[Franchise], genres: Seq[Genre], statistics: MangaStatistics)

object Manga {
    implicit val writes: OWrites[Manga] = Json.writes[Manga]
}
