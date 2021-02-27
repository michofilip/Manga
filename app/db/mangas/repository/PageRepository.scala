package db.mangas.repository

import db.mangas.MangasDbConfigProvider
import db.mangas.model.ChapterEntity
import dto.Page
import slick.jdbc.PostgresProfile.api._

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class PageRepository @Inject()(val mangasDbConfigProvider: MangasDbConfigProvider)(implicit ec: ExecutionContext) {

    def findByChapterId(chapterId: Int): Future[Seq[Page]] = mangasDbConfigProvider.run {
        ChapterEntity.table
            .filter(chapter => chapter.id === chapterId)
            .flatMap(chapter => chapter.pages)
            .sortBy(page => page.pageNr)
            .result
    }
}