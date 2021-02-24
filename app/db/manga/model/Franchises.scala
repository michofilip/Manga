package db.manga.model

import dto.Franchise
import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

class Franchises(tag: Tag) extends Table[Franchise](tag, "franchises") {

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def name = column[String]("name")

    def * = (name, id).mapTo[Franchise]

}

object Franchises {
    val table = TableQuery[Franchises]
}




