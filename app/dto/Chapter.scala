package dto

import play.api.libs.json.{Json, OWrites}

case class Chapter(number: Int, subNumber: Int, title: String, mangaId: Int, id: Int = 0)

object Chapter {
    implicit val writes: OWrites[Chapter] = Json.writes[Chapter]

    def tupled = (Chapter.apply _).tupled

}
