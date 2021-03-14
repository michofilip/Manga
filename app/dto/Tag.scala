package dto

import db.mangas.model.TagTable.TagEntity
import form.TagForm
import play.api.libs.json.{Json, OWrites}

case class Tag(id: Int, tag: String)

object Tag {
    implicit val writes: OWrites[Tag] = Json.writes[Tag]

    def fromEntity(tagEntity: TagEntity): Tag = {
        Tag(
            id = tagEntity.id,
            tag = tagEntity.tag
        )
    }

    def fromEntities(tagEntities: Seq[TagEntity]): Seq[Tag] = {
        tagEntities.map(fromEntity)
    }

    def toEntity(tagForm: TagForm): TagEntity = {
        TagEntity(
            accountId = tagForm.accountId,
            tag = tagForm.tag,
            id = tagForm.id.getOrElse(0)
        )
    }

}
