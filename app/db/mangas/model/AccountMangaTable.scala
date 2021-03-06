package db.mangas.model

import db.mangas.model.AccountMangaTable.AccountMangaEntity
import slick.jdbc.PostgresProfile.api._

class AccountMangaTable(t: Tag) extends Table[AccountMangaEntity](t, "account_manga") {

    def accountId = column[Int]("account_id")

    def mangaId = column[Int]("manga_id")

    def isInCollection = column[Boolean]("is_in_collection")

    def isRead = column[Boolean]("is_read")

    def isFavorite = column[Boolean]("is_favorite")

    def score = column[Option[Double]]("score")

    def * = (accountId, mangaId, isInCollection, isRead, isFavorite, score).mapTo[AccountMangaEntity]

    def accountFk = foreignKey("account_fk", accountId, AccountTable.all)(_.id)

    def account = accountFk.filter(_.id === accountId)

    def mangaFk = foreignKey("manga_fk", mangaId, MangaTable.all)(_.id)

    def manga = mangaFk.filter(_.id === mangaId)

    def tags = MangaTagTable.all.filter(_.mangaId === mangaId).flatMap(_.tag)

}

object AccountMangaTable {
    val all = TableQuery[AccountMangaTable]

    case class AccountMangaEntity(accountId: Int, mangaId: Int, isInCollection: Boolean, isRead: Boolean, isFavorite: Boolean, score: Option[Double])

}
