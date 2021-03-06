package dto

import db.mangas.model.GenreTable.GenreEntity
import play.api.libs.json.{Json, OWrites}

case class Genre(id: Int, name: String)

object Genre {
    implicit val writes: OWrites[Genre] = Json.writes[Genre]

    def fromEntity(genreEntity: GenreEntity): Genre = {
        Genre(
            id = genreEntity.id,
            name = genreEntity.name
        )
    }

    def fromEntities(genreEntities: Seq[GenreEntity]): Seq[Genre] = {
        genreEntities.map(fromEntity)
    }
}
