package db.mangas.model

import db.mangas.model.TagTable.TagEntity
import slick.jdbc.PostgresProfile.api._

class TagTable(t: Tag) extends Table[TagEntity](t, "tag") {

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def accountId = column[Int]("account_id")

    def tag = column[String]("tag")

    def * = (accountId, tag, id).mapTo[TagEntity]

    def accountTagIdx = index("account_tag_idx", (accountId, tag), unique = true)

    def accountFk = foreignKey("account_fk", accountId, AccountTable.all)(_.id)

    def mangas = MangaTagTable.all.filter(_.tagId === id).flatMap(_.manga)

}

object TagTable {
    val all = TableQuery[TagTable]

    case class TagEntity(accountId: Int, tag: String, id: Int = 0)

}
