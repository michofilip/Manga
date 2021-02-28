package db.mangas.model

import dto.Account
import slick.jdbc.PostgresProfile.api._

class AccountEntity(tag: Tag) extends Table[Account](tag, "account") {

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def userId = column[Int]("user_id")

    def isActive = column[Boolean]("is_active")

    def * = (userId, isActive, id).mapTo[Account]

    def accountUserIdx = index("account_user_idx", (id, userId), unique = true)

    def tags = TagEntity.table.filter(_.accountId === id)

    def mangas = AccountMangaEntity.table.filter(_.accountId === id).flatMap(_.manga)

}

object AccountEntity {
    val table = TableQuery[AccountEntity]
}
