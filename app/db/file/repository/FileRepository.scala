package db.file.repository

import db.file.model.Files
import dto.File
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.db.NamedDatabase
import slick.jdbc.JdbcProfile
import slick.jdbc.PostgresProfile.api._

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class FileRepository @Inject()(@NamedDatabase("files_db") val dbConfigProvider: DatabaseConfigProvider)
                              (implicit ec: ExecutionContext)
    extends HasDatabaseConfigProvider[JdbcProfile] {

    def findByKey(key: String): Future[Option[File]] = db.run {
        Files.table
            .filter(file => file.key === key)
            .result.headOption
    }
}