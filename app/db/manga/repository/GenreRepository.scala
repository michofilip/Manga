package db.manga.repository

import db.manga.model.{Genres, MangaGenres, Mangas}
import dto.Genre
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.db.NamedDatabase
import slick.jdbc.JdbcProfile
import slick.jdbc.PostgresProfile.api._

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class GenreRepository @Inject()(@NamedDatabase("mangas_db") val dbConfigProvider: DatabaseConfigProvider)
                               (implicit ec: ExecutionContext)
    extends HasDatabaseConfigProvider[JdbcProfile] {

    def findAll(): Future[Seq[Genre]] = db.run {
        Genres.table.result
    }

    def findAllByMangaId(mangaId: Int): Future[Seq[Genre]] = db.run {
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