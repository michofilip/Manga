package dto

import db.mangas.model.ChapterTable.ChapterEntity
import play.api.libs.json.{Json, OWrites}

case class Chapter(number: Int, subNumber: Int, title: String, mangaId: Int, id: Int = 0)

object Chapter {
    implicit val writes: OWrites[Chapter] = Json.writes[Chapter]

    def fromEntity(chapterEntity: ChapterEntity): Chapter = chapterEntity match {
        case ChapterEntity(number, subNumber, title, mangaId, id) => Chapter(number, subNumber, title, mangaId, id)
    }

    def fromEntities(chapterEntities: Seq[ChapterEntity]): Seq[Chapter] = {
        chapterEntities.map(fromEntity)
    }
}
