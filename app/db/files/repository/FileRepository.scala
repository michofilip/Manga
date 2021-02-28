package db.files.repository

import db.files.FilesDbConfigProvider
import db.files.model.FileEntity
import dto.File
import slick.jdbc.PostgresProfile.api._

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class FileRepository @Inject()(val filesDbConfigProvider: FilesDbConfigProvider)(implicit ec: ExecutionContext) {

    def findByKey(key: String): Future[Option[File]] = filesDbConfigProvider.run {
        FileEntity.table
            .filter(file => file.key === key)
            .result.headOption
    }
}