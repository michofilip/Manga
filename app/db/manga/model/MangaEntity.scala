package db.manga.model

import dto.Manga
import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

class MangaEntity(tag: Tag) extends Table[Manga](tag, "manga") {

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def title = column[String]("title")

    def * = (title, id).mapTo[Manga]

}

object MangaEntity {
    val table = TableQuery[MangaEntity]
}
