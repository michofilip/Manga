package utils

import scala.concurrent.Future
import scala.util.Failure

object ExceptionUtils {

    def futureFailure(throwable: Throwable): Future[Failure[Nothing]] = {
        Future.successful {
            Failure {
                throwable
            }
        }
    }

    def noSuchElementException(message: String): Future[Failure[Nothing]] = {
        futureFailure(new NoSuchElementException(message))
    }
}
