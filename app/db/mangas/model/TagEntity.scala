package db.mangas.model

import slick.jdbc.PostgresProfile.api._

class TagEntity(_tag: Tag) extends Table[dto.Tag](_tag, "tag") {

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def accountId = column[Int]("account_id")

    def tag = column[String]("tag")

    def * = (accountId, tag, id).mapTo[dto.Tag]

    def accountTagIdx = index("account_tag_idx", (accountId, tag), unique = true)

    def accountFk = foreignKey("account_fk", accountId, AccountTable.all)(_.id)

    def mangas = MangaTagEntity.table.filter(_.tagId === id).flatMap(_.manga)

}

object TagEntity {
    val table = TableQuery[TagEntity]
}
