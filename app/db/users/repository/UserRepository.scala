package db.users.repository

import db.users.UsersDbConfigProvider
import db.users.model.UserTable
import db.users.model.UserTable.UserEntity
import slick.jdbc.PostgresProfile.api._

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserRepository @Inject()(usersDbConfigProvider: UsersDbConfigProvider)(implicit ec: ExecutionContext) {

    def findAll(): Future[Seq[UserEntity]] = usersDbConfigProvider.run {
        UserTable.all.result
    }

    def findById(id: Int): Future[Option[UserEntity]] = usersDbConfigProvider.run {
        UserTable.all
            .filter(user => user.id === id)
            .result.headOption
    }

    def create(user: UserEntity): Future[UserEntity] = usersDbConfigProvider.run {
        (UserTable.all returning UserTable.all.map(user => user.id) into ((user, id) => user.copy(id = id))) += user
    }

    def update(user: UserEntity): Future[Int] = usersDbConfigProvider.run {
        val id = user.id

        UserTable.all
            .filter(user => user.id === id)
            .update(user)
    }

    def delete(id: Int): Future[Int] = usersDbConfigProvider.run {
        UserTable.all
            .filter(user => user.id === id)
            .delete
    }
}
