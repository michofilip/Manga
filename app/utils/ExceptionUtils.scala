package utils

import scala.concurrent.Future

object ExceptionUtils {
    def noSuchElementException(message: String): Future[Left[NoSuchElementException, Nothing]] = {
        Future.successful {
            Left {
                new NoSuchElementException(message)
            }
        }
    }
}
