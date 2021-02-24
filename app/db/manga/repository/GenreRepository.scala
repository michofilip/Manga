package db.manga.repository

import db.manga.MangasDbConfigProvider
import db.manga.model.{Genres, MangaGenres, Mangas}
import dto.Genre
import slick.jdbc.PostgresProfile.api._

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class GenreRepository @Inject()(val mangasDbConfigProvider: MangasDbConfigProvider)(implicit ec: ExecutionContext) {

    def findAll(): Future[Seq[Genre]] = mangasDbConfigProvider.run {
        Genres.table.result
    }

    def findAllByMangaId(mangaId: Int): Future[Seq[Genre]] = mangasDbConfigProvider.run {
        val query = for {
            manga <- Mangas.table if manga.id === mangaId
            mangaGenre <- MangaGenres.table if mangaGenre.mangaId === manga.id
            genre <- Genres.table if genre.id === mangaGenre.genreId
        } yield genre

        query.result
    }

    //    def findByName(name: String): Future[Option[Genre]] = db.run {
    //        Genres.table
    //            .filter(genre => genre.name === name)
    //            .result.headOption
    //    }
}