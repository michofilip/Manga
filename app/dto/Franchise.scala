package dto

import db.mangas.model.FranchiseTable.FranchiseEntity
import play.api.libs.json.{Json, OWrites}

case class Franchise(name: String, id: Int = 0)

object Franchise {
    implicit val writes: OWrites[Franchise] = Json.writes[Franchise]

    def fromEntity(franchiseEntity: FranchiseEntity): Franchise = franchiseEntity match {
        case FranchiseEntity(name, id) => Franchise(name, id)
    }

    def fromEntities(franchiseEntities: Seq[FranchiseEntity]): Seq[Franchise] = {
        franchiseEntities.map(fromEntity)
    }
}
