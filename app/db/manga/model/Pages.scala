package db.manga.model

import dto.Page
import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

class Pages(tag: Tag) extends Table[Page](tag, "pages") {

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def key = column[String]("key")

    def chapterId = column[Int]("chapter_id")

    def pageNr = column[Int]("page_nr")

    def * = (key, chapterId, pageNr, id).mapTo[Page]

    def chapter = foreignKey("chapter_fk", pageNr, Chapters.table)(_.id)

}

object Pages {
    val table = TableQuery[Pages]
}


