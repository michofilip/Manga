package dto

import db.mangas.model.AccountMangaTable.AccountMangaEntity
import play.api.libs.json.{Json, OWrites}

case class AccountManga(accountId: Int, manga: MangaV2, isInCollection: Boolean, isRead: Boolean, isFavorite: Boolean, score: Option[Double], tags: Seq[Tag])

object AccountManga {
    implicit val writes: OWrites[AccountManga] = Json.writes[AccountManga]

    def fromEntity(accountMangaEntity: AccountMangaEntity, manga: MangaV2, tags: Seq[Tag]): AccountManga = {
        AccountManga(
            accountId = accountMangaEntity.accountId,
            manga = manga,
            isInCollection = accountMangaEntity.isInCollection,
            isRead = accountMangaEntity.isRead,
            isFavorite = accountMangaEntity.isFavorite,
            score = accountMangaEntity.score,
            tags = tags
        )
    }
}
