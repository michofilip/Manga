package db.manga.model

import dto.Manga
import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

class Mangas(tag: Tag) extends Table[Manga](tag, "mangas") {

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def title = column[String]("title")

    def * = (title, id).mapTo[Manga]

}

object Mangas {
    val table = TableQuery[Mangas]
}