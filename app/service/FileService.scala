package service

import db.files.repository.FileRepository
import dto.File
import utils.ExceptionUtils

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class FileService @Inject()(val fileRepository: FileRepository)
                           (implicit ec: ExecutionContext) {

    def findByKey(key: String): Future[Either[Throwable, File]] = {
        fileRepository.findByKey(key).flatMap {
            case None =>
                ExceptionUtils.noSuchElementException(s"File key $key not found!")

            case Some(file) =>
                Future.successful {
                    Right {
                        file
                    }
                }
        }
    }

}
