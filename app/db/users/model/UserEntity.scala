package db.users.model

import dto.User
import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

class UserEntity(tag: Tag) extends Table[User](tag, "user") {

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def login = column[String]("login")

    def * = (login, id).mapTo[User]

}

object UserEntity {
    val table = TableQuery[UserEntity]
}