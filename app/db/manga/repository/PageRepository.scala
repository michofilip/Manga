package db.manga.repository

import db.manga.model.Pages
import dto.Page
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.db.NamedDatabase
import slick.jdbc.JdbcProfile
import slick.jdbc.PostgresProfile.api._

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class PageRepository @Inject()(@NamedDatabase("mangas_db") protected val dbConfigProvider: DatabaseConfigProvider)
                              (implicit ec: ExecutionContext)
    extends HasDatabaseConfigProvider[JdbcProfile] {

    def findByChapterId(chapterId: Int): Future[Seq[Page]] = db.run {
        Pages.table
            .filter(page => page.chapterId === chapterId)
            .sortBy(page => page.pageNr)
            .result
    }
}