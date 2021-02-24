package db.manga.repository

import db.manga.model.Chapters
import dto.Chapter
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.db.NamedDatabase
import slick.jdbc.JdbcProfile
import slick.jdbc.PostgresProfile.api._

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ChapterRepository @Inject()(@NamedDatabase("mangas_db") protected val dbConfigProvider: DatabaseConfigProvider)
                                 (implicit ec: ExecutionContext)
    extends HasDatabaseConfigProvider[JdbcProfile] {

    def findById(id: Int): Future[Option[Chapter]] = db.run {
        Chapters.table
            .filter(chapter => chapter.id === id)
            .result.headOption
    }

    def findAllByMangaId(mangaId: Int): Future[Seq[Chapter]] = db.run {
        Chapters.table
            .filter(chapter => chapter.mangaId === mangaId)
            .sortBy(chapter => (chapter.number, chapter.subNumber))
            .result
    }
}