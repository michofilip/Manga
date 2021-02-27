package db.mangas.model

import dto.Page
import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

class PageEntity(tag: Tag) extends Table[Page](tag, "page") {

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def key = column[String]("key")

    def chapterId = column[Int]("chapter_id")

    def pageNr = column[Int]("page_nr")

    def * = (key, chapterId, pageNr, id).mapTo[Page]

    def chapterFk = foreignKey("chapter_fk", pageNr, ChapterEntity.table)(_.id)

    def chapter = chapterFk.filter(_.id === chapterId)

}

object PageEntity {
    val table = TableQuery[PageEntity]
}
