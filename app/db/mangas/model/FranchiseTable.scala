package db.mangas.model

import db.mangas.model.FranchiseTable.FranchiseEntity
import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

class FranchiseTable(tag: Tag) extends Table[FranchiseEntity](tag, "franchise") {

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def name = column[String]("name")

    def * = (name, id).mapTo[FranchiseEntity]

    def mangas = MangaGenreEntity.table.filter(_.genreId === id).flatMap(_.manga)

}

object FranchiseTable {
    val all = TableQuery[FranchiseTable]

    case class FranchiseEntity(name: String, id: Int = 0)

}
