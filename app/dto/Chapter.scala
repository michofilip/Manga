package dto

import db.mangas.model.ChapterTable.ChapterEntity
import play.api.libs.json.{Json, OWrites}

case class Chapter(id: Int, number: Int, subNumber: Int, title: String)

object Chapter {
    implicit val writes: OWrites[Chapter] = Json.writes[Chapter]

    def fromEntity(chapterEntity: ChapterEntity): Chapter = {
        Chapter(
            id = chapterEntity.id,
            number = chapterEntity.number,
            subNumber = chapterEntity.subNumber,
            title = chapterEntity.title
        )
    }

    def fromEntities(chapterEntities: Seq[ChapterEntity]): Seq[Chapter] = {
        chapterEntities.map(fromEntity)
    }
}
