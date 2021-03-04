package db.mangas.model

import dto.AccountManga
import slick.jdbc.PostgresProfile.api._

class AccountMangaEntity(tag: Tag) extends Table[AccountManga](tag, "account_manga") {

    def accountId = column[Int]("account_id")

    def mangaId = column[Int]("manga_id")

    def isInCollection = column[Boolean]("is_in_collection")

    def isRead = column[Boolean]("is_read")

    def isFavorite = column[Boolean]("is_favorite")

    def score = column[Option[Int]]("score")

    def * = (accountId, mangaId, isInCollection, isRead, isFavorite, score).mapTo[AccountManga]

    def accountFk = foreignKey("account_fk", accountId, AccountTable.all)(_.id)

    def account = accountFk.filter(_.id === accountId)

    def mangaFk = foreignKey("manga_fk", mangaId, MangaTable.all)(_.id)

    def manga = mangaFk.filter(_.id === mangaId)

}

object AccountMangaEntity {
    val table = TableQuery[AccountMangaEntity]
}
