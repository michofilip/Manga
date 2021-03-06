package service

import db.users.repository.UserRepository
import dto.User
import form.UserForm
import utils.ExceptionUtils

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Success, Try}

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
                ExceptionUtils.noSuchElementException(s"User id $userId not found!")

            case Some(userEntity) =>
                Future.successful {
                    Success {
                        User.fromEntity(userEntity)
                    }
                }
        }
    }

    def create(userForm: UserForm): Future[User] = {
        val user = User.from(userForm)

        userRepository.create(User.toEntity(user))
            .map(User.fromEntity)
    }

    def update(userForm: UserForm): Future[Try[User]] = {
        val user = User.from(userForm)

        userRepository.exists(user.id).flatMap {
            case true =>
                ExceptionUtils.noSuchElementException(s"User id ${user.id} not found!")

            case false =>
                for {
                    _ <- userRepository.update(User.toEntity(user))
                    user <- findById(user.id)
                } yield user
        }
    }

    def delete(userId: Int): Future[Try[Unit]] = {
        userRepository.exists(userId).flatMap {
            case false =>
                ExceptionUtils.noSuchElementException(s"User id $userId not found!")

            case true =>
                userRepository.delete(userId).map { _ =>
                    Success()
                }
        }
    }

}
