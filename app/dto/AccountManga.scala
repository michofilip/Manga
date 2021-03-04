package dto

import db.mangas.model.AccountMangaTable.AccountMangaEntity
import db.mangas.model.MangaTable.MangaEntity
import play.api.libs.json.{Json, OWrites}

case class AccountManga(manga: Manga, isInCollection: Boolean, isRead: Boolean, isFavorite: Boolean, score: Option[Int])

object AccountManga {
    implicit val writes: OWrites[AccountManga] = Json.writes[AccountManga]

    def fromEntity(accountMangaEntity: AccountMangaEntity, mangaEntity: MangaEntity): AccountManga = {
        AccountManga(
            manga = Manga.fromEntity(mangaEntity),
            isInCollection = accountMangaEntity.isInCollection,
            isRead = accountMangaEntity.isRead,
            isFavorite = accountMangaEntity.isFavorite,
            score = accountMangaEntity.score
        )
    }
}
