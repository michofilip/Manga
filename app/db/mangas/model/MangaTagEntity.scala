package db.mangas.model

import slick.jdbc.PostgresProfile.api._

class MangaTagEntity(_tag: Tag) extends Table[(Int, Int)](_tag, "manga_tag") {

    def mangaId = column[Int]("manga_id")

    def tagId = column[Int]("tag_id")

    def * = (mangaId, tagId)

    def mangaFk = foreignKey("manga_fk", mangaId, MangaEntity.table)(_.id)

    def manga = mangaFk.filter(_.id === mangaId)

    def tagFk = foreignKey("tag_fk", tagId, TagEntity.table)(_.id)

    def tag = tagFk.filter(_.id === tagId)

}

object MangaTagEntity {
    val table = TableQuery[MangaTagEntity]
}
