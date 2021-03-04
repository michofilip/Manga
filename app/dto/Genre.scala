package dto

import db.mangas.model.GenreTable.GenreEntity
import play.api.libs.json.{Json, OWrites}

case class Genre(name: String, id: Int = 0)

object Genre {
    implicit val writes: OWrites[Genre] = Json.writes[Genre]

    def fromEntity(genreEntity: GenreEntity): Genre = genreEntity match {
        case GenreEntity(name, id) => Genre(name, id)
    }

    def fromEntities(genreEntities: Seq[GenreEntity]): Seq[Genre] = {
        genreEntities.map(fromEntity)
    }
}
