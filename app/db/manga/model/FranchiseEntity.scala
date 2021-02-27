package db.manga.model

import dto.Franchise
import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

class FranchiseEntity(tag: Tag) extends Table[Franchise](tag, "franchise") {

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def name = column[String]("name")

    def * = (name, id).mapTo[Franchise]

}

object FranchiseEntity {
    val table = TableQuery[FranchiseEntity]
}
