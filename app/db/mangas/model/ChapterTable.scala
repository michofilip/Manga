package db.mangas.model

import db.mangas.model.ChapterTable.ChapterEntity
import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

class ChapterTable(tag: Tag) extends Table[ChapterEntity](tag, "chapter") {

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def number = column[Int]("number")

    def subNumber = column[Int]("sub_number")

    def title = column[String]("title")

    def mangaId = column[Int]("manga_id")

    def * = (number, subNumber, title, mangaId, id).mapTo[ChapterEntity]

    def mangaFk = foreignKey("manga_fk", mangaId, MangaTable.all)(_.id)

    def manga = mangaFk.filter(_.id === mangaId)

    def pages = PageEntity.table.filter(_.chapterId === id)

}

object ChapterTable {
    val all = TableQuery[ChapterTable]

    case class ChapterEntity(number: Int, subNumber: Int, title: String, mangaId: Int, id: Int = 0)

}
