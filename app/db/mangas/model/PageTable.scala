package db.mangas.model

import db.mangas.model.PageTable.PageEntity
import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

class PageTable(t: Tag) extends Table[PageEntity](t, "page") {

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def key = column[String]("key")

    def chapterId = column[Int]("chapter_id")

    def pageNr = column[Int]("page_nr")

    def * = (key, chapterId, pageNr, id).mapTo[PageEntity]

    def chapterFk = foreignKey("chapter_fk", pageNr, ChapterTable.all)(_.id)

    def chapter = chapterFk.filter(_.id === chapterId)

}

object PageTable {
    val all = TableQuery[PageTable]

    case class PageEntity(key: String, chapterId: Int, pageNr: Int, id: Int = 0)

}
