package db.users.model

import db.users.model.UserTable.UserEntity
import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

class UserTable(t: Tag) extends Table[UserEntity](t, "user") {

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def login = column[String]("login")

    def * = (login, id).mapTo[UserEntity]

}

object UserTable {
    val all = TableQuery[UserTable]

    case class UserEntity(login: String, id: Int = 0)

}
