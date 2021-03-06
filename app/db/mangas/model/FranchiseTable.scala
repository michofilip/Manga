package db.mangas.model

import db.mangas.model.FranchiseTable.FranchiseEntity
import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

class FranchiseTable(t: Tag) extends Table[FranchiseEntity](t, "franchise") {

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def name = column[String]("name")

    def * = (name, id).mapTo[FranchiseEntity]

    def mangas = MangaFranchiseTable.all.filter(_.franchiseId === id).flatMap(_.manga)

}

object FranchiseTable {
    val all = TableQuery[FranchiseTable]

    case class FranchiseEntity(name: String, id: Int = 0)

}
