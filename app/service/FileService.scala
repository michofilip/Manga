package service

import db.file.repository.FileRepository
import dto.File

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class FileService @Inject()(val fileRepository: FileRepository)
                           (implicit ec: ExecutionContext) {

    def findByKey(key: String): Future[Either[Throwable, File]] = {
        fileRepository.findByKey(key).flatMap {
            case None =>
                Future.successful {
                    Left {
                        new NoSuchElementException(s"File key $key not found!")
                    }
                }

            case Some(file) =>
                Future.successful {
                    Right {
                        file
                    }
                }
        }
    }

}
