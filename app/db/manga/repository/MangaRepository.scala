package db.manga.repository

import db.manga.MangasDbConfigProvider
import db.manga.model.{Franchises, Genres, MangaFranchises, MangaGenres, Mangas}
import dto.Manga
import slick.jdbc.PostgresProfile.api._

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class MangaRepository @Inject()(val mangasDbConfigProvider: MangasDbConfigProvider)(implicit ec: ExecutionContext) {

    def findAll(): Future[Seq[Manga]] = mangasDbConfigProvider.run {
        Mangas.table.result
    }

    def findById(id: Int): Future[Option[Manga]] = mangasDbConfigProvider.run {
        Mangas.table
            .filter(manga => manga.id === id)
            .result.headOption
    }

    def findAllByTitle(title: String): Future[Seq[Manga]] = mangasDbConfigProvider.run {
        val titleLike = s"%$title%"

        Mangas.table
            .filter(manga => manga.title.toLowerCase like titleLike)
            .result
    }

    def findAllByFranchise(franchise: String): Future[Seq[Manga]] = mangasDbConfigProvider.run {
        val franchiseLike = s"%$franchise%"

        val query = for {
            manga <- Mangas.table
            mangaFranchise <- MangaFranchises.table if manga.id === mangaFranchise.mangaId
            franchise <- Franchises.table if (franchise.id === mangaFranchise.franchiseId) && (franchise.name.toLowerCase like franchiseLike)
        } yield manga

        query.distinct.result
    }

    def findAllByGenres(genres: Seq[String]): Future[Seq[Manga]] = mangasDbConfigProvider.run {
        val genresLowerCase = genres.map(_.toLowerCase)

        val query = for {
            manga <- Mangas.table
            mangaGenre <- MangaGenres.table if manga.id === mangaGenre.mangaId
            genre <- Genres.table if genre.id === mangaGenre.genreId && genre.name.toLowerCase.inSet(genresLowerCase)
        } yield manga

        query.distinct.result
    }
}