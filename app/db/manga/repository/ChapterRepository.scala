package db.manga.repository

import db.manga.MangasDbConfigProvider
import db.manga.model.Chapters
import dto.Chapter
import slick.jdbc.PostgresProfile.api._

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ChapterRepository @Inject()(val mangasDbConfigProvider: MangasDbConfigProvider)(implicit ec: ExecutionContext) {

    def findById(id: Int): Future[Option[Chapter]] = mangasDbConfigProvider.run {
        Chapters.table
            .filter(chapter => chapter.id === id)
            .result.headOption
    }

    def findAllByMangaId(mangaId: Int): Future[Seq[Chapter]] = mangasDbConfigProvider.run {
        Chapters.table
            .filter(chapter => chapter.mangaId === mangaId)
            .sortBy(chapter => (chapter.number, chapter.subNumber))
            .result
    }
}