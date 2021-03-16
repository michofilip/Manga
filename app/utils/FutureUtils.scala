package utils

import scala.concurrent.Future
import scala.util.{Failure, Success, Try}

object FutureUtils {

    def futureSuccess: Future[Try[Unit]] = {
        futureSuccess((): Unit)
    }

    def futureSuccess[T](value: T): Future[Try[T]] = {
        Future.successful {
            Success {
                value
            }
        }
    }

    def futureFailure(throwable: Throwable): Future[Try[Nothing]] = {
        Future.successful {
            Failure {
                throwable
            }
        }
    }

    def noSuchElementException(message: String): Future[Try[Nothing]] = {
        futureFailure(new NoSuchElementException(message))
    }
}
