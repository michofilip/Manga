package dto

import db.mangas.model.TagTable.TagEntity
import play.api.libs.json.{Json, OWrites}

case class Tag(accountId: Int, tag: String, id: Int = 0)

object Tag {
    implicit val writes: OWrites[Tag] = Json.writes[Tag]

    def fromEntity(tagEntity: TagEntity): Tag = tagEntity match {
        case TagEntity(accountId, tag, id) => Tag(accountId, tag, id)
    }

    def fromEntities(tagEntities: Seq[TagEntity]): Seq[Tag] = {
        tagEntities.map(fromEntity)
    }

}
