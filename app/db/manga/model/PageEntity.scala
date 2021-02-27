package db.manga.model

import dto.Page
import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

class PageEntity(tag: Tag) extends Table[Page](tag, "page") {

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def key = column[String]("key")

    def chapterId = column[Int]("chapter_id")

    def pageNr = column[Int]("page_nr")

    def * = (key, chapterId, pageNr, id).mapTo[Page]

    def chapter = foreignKey("chapter_fk", pageNr, ChapterEntity.table)(_.id)

}

object PageEntity {
    val table = TableQuery[PageEntity]
}
