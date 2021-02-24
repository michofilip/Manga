package dto

import play.api.libs.json.{Json, OWrites}

case class ChapterDetails(chapter: Chapter, previousChapter: Option[Chapter], nextChapter: Option[Chapter], pages: Seq[Page])

object ChapterDetails {
    implicit val writes: OWrites[ChapterDetails] = Json.writes[ChapterDetails]
}

