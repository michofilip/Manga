package db.mangas.model

import db.mangas.model.GenreTable.GenreEntity
import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

class GenreTable(tag: Tag) extends Table[GenreEntity](tag, "genre") {

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def name = column[String]("name")

    def * = (name, id).mapTo[GenreEntity]

    def mangas = MangaGenreTable.all.filter(_.genreId === id).flatMap(_.manga)

}

object GenreTable {
    val all = TableQuery[GenreTable]

    case class GenreEntity(name: String, id: Int = 0)

}
