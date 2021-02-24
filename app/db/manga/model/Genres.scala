package db.manga.model

import dto.Genre
import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

class Genres(tag: Tag) extends Table[Genre](tag, "genres") {

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def name = column[String]("name")

    def * = (name, id).mapTo[Genre]

}

object Genres {
    val table = TableQuery[Genres]
}


