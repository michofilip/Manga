package db.manga.model

import dto.Chapter
import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

class ChapterEntity(tag: Tag) extends Table[Chapter](tag, "chapter") {

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def number = column[Int]("number")

    def subNumber = column[Int]("sub_number")

    def title = column[String]("title")

    def mangaId = column[Int]("manga_id")

    def * = (number, subNumber, title, mangaId, id).mapTo[Chapter]

    def mangaFk = foreignKey("manga_fk", mangaId, MangaEntity.table)(_.id)

    def manga = mangaFk.filter(_.id === mangaId)

    def pages = PageEntity.table.filter(_.chapterId === id)

}

object ChapterEntity {
    val table = TableQuery[ChapterEntity]
}
