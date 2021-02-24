package dto

import play.api.libs.json.{Json, OWrites}

case class Franchise(name: String, id: Int = 0)

object Franchise {
    implicit val writes: OWrites[Franchise] = Json.writes[Franchise]

    def tupled = (Franchise.apply _).tupled
}



