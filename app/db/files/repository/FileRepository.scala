package db.files.repository

import db.files.FilesDbConfigProvider
import db.files.model.FileTable
import db.files.model.FileTable.FileEntity
import slick.jdbc.PostgresProfile.api._

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class FileRepository @Inject()(val filesDbConfigProvider: FilesDbConfigProvider)(implicit ec: ExecutionContext) {

    def findByKey(key: String): Future[Option[FileEntity]] = filesDbConfigProvider.run {
        FileTable.all
            .filter(file => file.key === key)
            .result.headOption
    }
}
