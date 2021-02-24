package db.manga.model

import dto.Chapter
import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

class Chapters(tag: Tag) extends Table[Chapter](tag, "chapters") {

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def number = column[Int]("number")

    def subNumber = column[Int]("sub_number")

    def title = column[String]("title")

    def mangaId = column[Int]("manga_id")

    def * = (number, subNumber, title, mangaId, id).mapTo[Chapter]

    def manga = foreignKey("manga_fk", mangaId, Mangas.table)(_.id)

}

object Chapters {
    val table = TableQuery[Chapters]
}
