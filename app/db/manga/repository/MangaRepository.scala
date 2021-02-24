package db.manga.repository

import db.manga.model.{Franchises, Genres, MangaFranchises, MangaGenres, Mangas}
import dto.Manga
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.db.NamedDatabase
import slick.jdbc.JdbcProfile
import slick.jdbc.PostgresProfile.api._

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class MangaRepository @Inject()(@NamedDatabase("mangas_db") val dbConfigProvider: DatabaseConfigProvider)
                               (implicit ec: ExecutionContext)
    extends HasDatabaseConfigProvider[JdbcProfile] {

    def findAll(): Future[Seq[Manga]] = db.run {
        Mangas.table.result
    }

    def findById(id: Int): Future[Option[Manga]] = db.run {
        Mangas.table
            .filter(manga => manga.id === id)
            .result.headOption
    }

    def findAllByTitle(title: String): Future[Seq[Manga]] = db.run {
        val titleLike = s"%$title%"

        Mangas.table
            .filter(manga => manga.title.toLowerCase like titleLike)
            .result
    }

    def findAllByFranchise(franchise: String): Future[Seq[Manga]] = db.run {
        val franchiseLike = s"%$franchise%"

        val query = for {
            manga <- Mangas.table
            mangaFranchise <- MangaFranchises.table if manga.id === mangaFranchise.mangaId
            franchise <- Franchises.table if (franchise.id === mangaFranchise.franchiseId) && (franchise.name.toLowerCase like franchiseLike)
        } yield manga

        query.distinct.result
    }

    def findAllByGenres(genres: Seq[String]): Future[Seq[Manga]] = db.run {
        val genresLowerCase = genres.map(_.toLowerCase)

        val query = for {
            manga <- Mangas.table
            mangaGenre <- MangaGenres.table if manga.id === mangaGenre.mangaId
            genre <- Genres.table if genre.id === mangaGenre.genreId && genre.name.toLowerCase.inSet(genresLowerCase)
        } yield manga

        query.distinct.result
    }
}