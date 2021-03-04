package dto

import db.mangas.model.PageTable.PageEntity
import play.api.libs.json.{Json, OWrites}

case class Page(key: String, chapterId: Int, pageNr: Int, id: Int = 0)

object Page {
    implicit val writes: OWrites[Page] = Json.writes[Page]

    def fromEntity(pageEntity: PageEntity): Page = pageEntity match {
        case PageEntity(key, chapterId, pageNr, id) => Page(key, chapterId, pageNr, id)
    }

    def fromEntities(pageEntities: Seq[PageEntity]): Seq[Page] = {
        pageEntities.map(fromEntity)
    }
}
