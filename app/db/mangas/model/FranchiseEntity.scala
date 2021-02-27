package db.mangas.model

import dto.Franchise
import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

class FranchiseEntity(tag: Tag) extends Table[Franchise](tag, "franchise") {

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def name = column[String]("name")

    def * = (name, id).mapTo[Franchise]

    def mangas = MangaGenreEntity.table.filter(_.genreId === id).flatMap(_.manga)

}

object FranchiseEntity {
    val table = TableQuery[FranchiseEntity]
}
