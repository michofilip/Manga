package service

import db.users.repository.UserRepository
import dto.User
import form.UserForm
import utils.ExceptionUtils

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserService @Inject()(val userRepository: UserRepository)
                           (implicit ec: ExecutionContext) {

    def findAll(): Future[Seq[User]] = {
        userRepository.findAll()
    }

    def findById(userId: Int): Future[Either[Throwable, User]] = {
        userRepository.findById(userId).flatMap {
            case None =>
                ExceptionUtils.noSuchElementException(s"User id $userId not found!")

            case Some(user) =>
                Future.successful {
                    Right {
                        user
                    }
                }
        }
    }

    def create(userForm: UserForm): Future[User] = {
        val user = User.from(userForm)

        userRepository.create(user)
    }

    def update(userForm: UserForm): Future[Either[Throwable, User]] = {
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

    def delete(userId: Int): Future[Either[Throwable, Unit]] = {
        userRepository.exists(userId).flatMap {
            case false =>
                ExceptionUtils.noSuchElementException(s"User id ${userId} not found!")

            case true =>
                userRepository.delete(userId).map(_ => Right())
        }
    }

}
