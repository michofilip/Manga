package dto

import play.api.libs.json.{Json, OWrites}

case class Page(key: String, chapterId: Int, pageNr: Int, id: Int = 0)

object Page {
    implicit val writes: OWrites[Page] = Json.writes[Page]

    def tupled = (Page.apply _).tupled
}
