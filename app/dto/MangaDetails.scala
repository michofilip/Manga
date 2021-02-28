package dto

import play.api.libs.json.{Json, OWrites}

case class MangaDetails(manga: Manga, franchises: Seq[Franchise], genres: Seq[Genre], chapters: Seq[Chapter])

object MangaDetails {
    implicit val writes: OWrites[MangaDetails] = Json.writes[MangaDetails]
}
