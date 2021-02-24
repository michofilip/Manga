package db.manga.repository

import db.manga.MangasDbConfigProvider
import db.manga.model.Pages
import dto.Page
import slick.jdbc.PostgresProfile.api._

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class PageRepository @Inject()(val mangasDbConfigProvider: MangasDbConfigProvider)(implicit ec: ExecutionContext) {

    def findByChapterId(chapterId: Int): Future[Seq[Page]] = mangasDbConfigProvider.run {
        Pages.table
            .filter(page => page.chapterId === chapterId)
            .sortBy(page => page.pageNr)
            .result
    }
}