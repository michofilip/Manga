package db.manga.repository

import db.manga.MangasDbConfigProvider
import db.manga.model.{GenreEntity, MangaGenreEntity, MangaEntity}
import dto.Genre
import slick.jdbc.PostgresProfile.api._

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class GenreRepository @Inject()(val mangasDbConfigProvider: MangasDbConfigProvider)(implicit ec: ExecutionContext) {

    def findAll(): Future[Seq[Genre]] = mangasDbConfigProvider.run {
        GenreEntity.table.result
    }

    def findAllByMangaId(mangaId: Int): Future[Seq[Genre]] = mangasDbConfigProvider.run {
        val query = for {
            manga <- MangaEntity.table if manga.id === mangaId
            mangaGenre <- MangaGenreEntity.table if mangaGenre.mangaId === manga.id
            genre <- GenreEntity.table if genre.id === mangaGenre.genreId
        } yield genre

        query.result
    }

    //    def findByName(name: String): Future[Option[Genre]] = db.run {
    //        Genres.table
    //            .filter(genre => genre.name === name)
    //            .result.headOption
    //    }
}