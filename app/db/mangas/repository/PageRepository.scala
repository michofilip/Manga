package db.mangas.repository

import db.mangas.MangasDbConfigProvider
import db.mangas.model.ChapterTable
import db.mangas.model.PageTable.PageEntity
import slick.jdbc.PostgresProfile.api._

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class PageRepository @Inject()(mangasDbConfigProvider: MangasDbConfigProvider)(implicit ec: ExecutionContext) {

    def findByChapterId(chapterId: Int): Future[Seq[PageEntity]] = mangasDbConfigProvider.run {
        ChapterTable.all
            .filter(chapter => chapter.id === chapterId)
            .flatMap(chapter => chapter.pages)
            .sortBy(page => page.pageNr)
            .result
    }
}
