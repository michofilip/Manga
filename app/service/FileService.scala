package service

import db.files.repository.FileRepository
import dto.File
import utils.FutureUtils

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

@Singleton
class FileService @Inject()(val fileRepository: FileRepository)
                           (implicit ec: ExecutionContext) {

    def findByKey(key: String): Future[Try[File]] = {
        fileRepository.findByKey(key).flatMap {
            case None =>
                FutureUtils.noSuchElementException(s"File key $key not found!")

            case Some(fileEntity) =>
                FutureUtils.futureSuccess(File.fromEntity(fileEntity))
        }
    }

}
