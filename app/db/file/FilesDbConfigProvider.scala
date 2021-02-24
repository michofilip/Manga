package db.file

import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.db.NamedDatabase
import slick.dbio.{DBIOAction, NoStream}
import slick.jdbc.JdbcProfile

import javax.inject.{Inject, Singleton}
import scala.concurrent.Future

@Singleton
class FilesDbConfigProvider @Inject()(@NamedDatabase("files_db") protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {
    final def run[R](a: DBIOAction[R, NoStream, Nothing]): Future[R] = db.run(a)
}
