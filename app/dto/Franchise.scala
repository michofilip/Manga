package dto

import db.mangas.model.FranchiseTable.FranchiseEntity
import play.api.libs.json.{Json, OWrites}

case class Franchise(id: Int, name: String)

object Franchise {
    implicit val writes: OWrites[Franchise] = Json.writes[Franchise]

    def fromEntity(franchiseEntity: FranchiseEntity): Franchise = {
        Franchise(
            id = franchiseEntity.id,
            name = franchiseEntity.name
        )
    }

    def fromEntities(franchiseEntities: Seq[FranchiseEntity]): Seq[Franchise] = {
        franchiseEntities.map(fromEntity)
    }
}
