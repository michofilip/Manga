package dto

import db.mangas.model.MangaTable.MangaEntity
import play.api.libs.json.{Json, OWrites}

case class Manga(title: String, id: Int = 0)

object Manga {
    implicit val writes: OWrites[Manga] = Json.writes[Manga]

    def fromEntity(mangaEntity: MangaEntity): Manga = mangaEntity match {
        case MangaEntity(title, id) => Manga(title, id)
    }

    def fromEntities(mangaEntities: Seq[MangaEntity]): Seq[Manga] = {
        mangaEntities.map(fromEntity)
    }
}
