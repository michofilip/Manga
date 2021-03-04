package dto

import db.mangas.model.ChapterTable.ChapterEntity
import play.api.libs.json.{Json, OWrites}

case class ChapterDetails(chapter: Chapter, previousChapter: Option[Chapter], nextChapter: Option[Chapter], pages: Seq[Page])

object ChapterDetails {
    implicit val writes: OWrites[ChapterDetails] = Json.writes[ChapterDetails]

    def fromEntity(chapter: ChapterEntity, previousChapter: Option[ChapterEntity], nextChapter: Option[ChapterEntity], pages: Seq[Page]): ChapterDetails = {
        ChapterDetails(
            chapter = Chapter.fromEntity(chapter),
            previousChapter = previousChapter.map(Chapter.fromEntity),
            nextChapter = nextChapter.map(Chapter.fromEntity),
            pages = pages
        )
    }
}
