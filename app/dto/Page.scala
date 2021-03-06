package dto

import db.mangas.model.PageTable.PageEntity
import play.api.libs.json.{Json, OWrites}

case class Page(id: Int, key: String, pageNr: Int)

object Page {
    implicit val writes: OWrites[Page] = Json.writes[Page]

    def fromEntity(pageEntity: PageEntity): Page = {
        Page(
            id = pageEntity.id,
            key = pageEntity.key,
            pageNr = pageEntity.pageNr
        )
    }

    def fromEntities(pageEntities: Seq[PageEntity]): Seq[Page] = {
        pageEntities.map(fromEntity)
    }
}
