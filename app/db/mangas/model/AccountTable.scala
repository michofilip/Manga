package db.mangas.model

import db.mangas.model.AccountTable.AccountEntity
import slick.jdbc.PostgresProfile.api._

class AccountTable(t: Tag) extends Table[AccountEntity](t, "account") {

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def userId = column[Int]("user_id")

    def isActive = column[Boolean]("is_active")

    def * = (userId, isActive, id).mapTo[AccountEntity]

    def accountUserIdx = index("account_user_idx", (id, userId), unique = true)

    def tags = TagTable.all.filter(_.accountId === id)

    def accountMangas = AccountMangaTable.all.filter(_.accountId === id)

}

object AccountTable {
    val all = TableQuery[AccountTable]

    case class AccountEntity(userId: Int, isActive: Boolean, id: Int = 0)

}
