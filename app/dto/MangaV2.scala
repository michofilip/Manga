package dto

import play.api.libs.json.{Json, OWrites}

case class MangaV2(id: Int, title: String, franchises: Seq[Franchise], genres: Seq[Genre], avgScore: Option[Double])

object MangaV2 {
    implicit val writes: OWrites[MangaV2] = Json.writes[MangaV2]
}
