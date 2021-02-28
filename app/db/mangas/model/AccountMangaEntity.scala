package db.mangas.model

import slick.jdbc.PostgresProfile.api._

class AccountMangaEntity(tag: Tag) extends Table[(Int, Int)](tag, "account_manga") {

    def accountId = column[Int]("account_id")

    def mangaId = column[Int]("manga_id")

    def * = (accountId, mangaId)

    def accountFk = foreignKey("account_fk", accountId, AccountEntity.table)(_.id)

    def account = accountFk.filter(_.id === accountId)

    def mangaFk = foreignKey("manga_fk", mangaId, MangaEntity.table)(_.id)

    def manga = mangaFk.filter(_.id === mangaId)

}

object AccountMangaEntity {
    val table = TableQuery[AccountMangaEntity]
}
