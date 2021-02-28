package db.users.repository

import db.users.UsersDbConfigProvider
import db.users.model.UserEntity
import dto.User
import slick.jdbc.PostgresProfile.api._

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserRepository @Inject()(val usersDbConfigProvider: UsersDbConfigProvider)(implicit ec: ExecutionContext) {

    def findAll(): Future[Seq[User]] = usersDbConfigProvider.run {
        UserEntity.table.result
    }

    def findById(id: Int): Future[Option[User]] = usersDbConfigProvider.run {
        UserEntity.table
            .filter(user => user.id === id)
            .result.headOption
    }

    def create(user: User): Future[User] = usersDbConfigProvider.run {
        (UserEntity.table returning UserEntity.table.map(user => user.id) into ((user, id) => user.copy(id = id))) += user
    }

    def update(user: User): Future[Int] = {
        val id = user.id

        val query = UserEntity.table
            .filter(_.id === id)
            .update(user)

        println(user)
        println(query.statements.head)

        usersDbConfigProvider.run {
            query
        }
    }

    def delete(id: Int): Future[Int] = usersDbConfigProvider.run {
        UserEntity.table
            .filter(user => user.id === id)
            .delete
    }
}
