package service

import db.users.repository.UserRepository
import dto.User
import form.UserForm
import utils.ExceptionUtils

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Success, Try}

@Singleton
class UserService @Inject()(val userRepository: UserRepository)
                           (implicit ec: ExecutionContext) {

    def findAll(): Future[Seq[User]] = {
        userRepository.findAll()
    }

    def findById(userId: Int): Future[Try[User]] = {
        userRepository.findById(userId).flatMap {
            case None =>
                ExceptionUtils.noSuchElementException(s"User id $userId not found!")

            case Some(user) =>
                Future.successful {
                    Success {
                        user
                    }
                }
        }
    }

    def create(userForm: UserForm): Future[User] = {
        val user = User.from(userForm)

        userRepository.create(user)
    }

    def update(userForm: UserForm): Future[Try[User]] = {
        val user = User.from(userForm)

        userRepository.exists(user.id).flatMap {
            case true =>
                ExceptionUtils.noSuchElementException(s"User id ${user.id} not found!")

            case false =>
                for {
                    _ <- userRepository.update(user)
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
