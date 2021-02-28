package utils

import scala.concurrent.Future
import scala.util.Failure

object ExceptionUtils {
    def noSuchElementException(message: String): Future[Failure[Nothing]] = {
        Future.successful {
            Failure {
                new NoSuchElementException(message)
            }
        }
    }
}
