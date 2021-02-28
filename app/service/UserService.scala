package service

import db.users.repository.UserRepository
import dto.User
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

}
