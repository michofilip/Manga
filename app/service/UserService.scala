package service

import db.users.repository.UserRepository
import dto.User
import form.UserForm
import utils.FutureUtils

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

@Singleton
class UserService @Inject()(userRepository: UserRepository)
                           (implicit ec: ExecutionContext) {

    def findAll(): Future[Seq[User]] = {
        userRepository.findAll()
            .map(User.fromEntities)
    }

    def findById(userId: Int): Future[Try[User]] = {
        userRepository.findById(userId).flatMap {
            case None =>
                FutureUtils.noSuchElementException(s"User id $userId not found!")

            case Some(userEntity) =>
                FutureUtils.futureSuccess(User.fromEntity(userEntity))
        }
    }

    // TODO return try of user
    def create(userForm: UserForm): Future[User] = {
        val user = User.from(userForm)

        userRepository.create(User.toEntity(user))
            .map(User.fromEntity)
    }

    def update(userForm: UserForm): Future[Try[User]] = {
        val user = User.from(userForm)
        val userEntity = User.toEntity(user)

        userRepository.update(userEntity).flatMap {
            case 0 =>
                FutureUtils.noSuchElementException(s"User id ${user.id} not found!")

            case _ =>
                FutureUtils.futureSuccess(user)
        }
    }

    def delete(userId: Int): Future[Try[Unit]] = {
        userRepository.delete(userId).flatMap {
            case 0 =>
                FutureUtils.noSuchElementException(s"User id $userId not found!")

            case _ =>
                FutureUtils.futureSuccess
        }
    }

}
