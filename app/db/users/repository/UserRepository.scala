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

    def exists(id: Int): Future[Boolean] = usersDbConfigProvider.run {
        UserEntity.table
            .filter(user => user.id === id)
            .exists
            .result
    }

    def create(user: User): Future[User] = usersDbConfigProvider.run {
        (UserEntity.table returning UserEntity.table.map(user => user.id) into ((user, id) => user.copy(id = id))) += user
    }

    def update(user: User): Future[Int] = usersDbConfigProvider.run {
        val id = user.id

        UserEntity.table
            .filter(user => user.id === id)
            .update(user)
    }

    def delete(id: Int): Future[Int] = usersDbConfigProvider.run {
        UserEntity.table
            .filter(user => user.id === id)
            .delete
    }
}
