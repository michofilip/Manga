package db.mangas.model

import slick.jdbc.PostgresProfile.api._

class MangaTagTable(t: Tag) extends Table[(Int, Int)](t, "manga_tag") {

    def mangaId = column[Int]("manga_id")

    def tagId = column[Int]("tag_id")

    def * = (mangaId, tagId)

    def mangaFk = foreignKey("manga_fk", mangaId, MangaTable.all)(_.id)

    def manga = mangaFk.filter(_.id === mangaId)

    def tagFk = foreignKey("tag_fk", tagId, TagTable.all)(_.id, onUpdate = ForeignKeyAction.Cascade, onDelete = ForeignKeyAction.Cascade)

    def tag = tagFk.filter(_.id === tagId)

}

object MangaTagTable {
    val all = TableQuery[MangaTagTable]
}
