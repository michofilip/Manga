package db.mangas.repository

import db.mangas.MangasDbConfigProvider
import db.mangas.model.ChapterTable.ChapterEntity
import db.mangas.model.{ChapterTable, MangaTable}
import slick.jdbc.PostgresProfile.api._

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ChapterRepository @Inject()(mangasDbConfigProvider: MangasDbConfigProvider)(implicit ec: ExecutionContext) {

    def findById(id: Int): Future[Option[ChapterEntity]] = mangasDbConfigProvider.run {
        ChapterTable.all
            .filter(chapter => chapter.id === id)
            .result.headOption
    }

    def findAllByMangaId(mangaId: Int): Future[Seq[ChapterEntity]] = mangasDbConfigProvider.run {
        MangaTable.all
            .filter(manga => manga.id === mangaId)
            .flatMap(manga => manga.chapters)
            .sortBy(chapter => (chapter.number, chapter.subNumber))
            .result
    }
}
