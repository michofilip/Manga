package dto

import play.api.libs.json.{Json, OWrites}

case class MangaWithChapters(manga: Manga, chapters: Seq[Chapter])

object MangaWithChapters {
    implicit val writes: OWrites[MangaWithChapters] = Json.writes[MangaWithChapters]
}
