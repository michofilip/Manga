package db.manga.repository

import db.manga.MangasDbConfigProvider
import db.manga.model.{FranchiseEntity, GenreEntity, MangaFranchiseEntity, MangaGenreEntity, MangaEntity}
import dto.Manga
import slick.jdbc.PostgresProfile.api._

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class MangaRepository @Inject()(val mangasDbConfigProvider: MangasDbConfigProvider)(implicit ec: ExecutionContext) {

    def findAll(): Future[Seq[Manga]] = mangasDbConfigProvider.run {
        MangaEntity.table.result
    }

    def findById(id: Int): Future[Option[Manga]] = mangasDbConfigProvider.run {
        MangaEntity.table
            .filter(manga => manga.id === id)
            .result.headOption
    }

    def findAllByTitle(title: String): Future[Seq[Manga]] = mangasDbConfigProvider.run {
        val titleLike = s"%$title%"

        MangaEntity.table
            .filter(manga => manga.title.toLowerCase like titleLike)
            .result
    }

    def findAllByFranchise(franchise: String): Future[Seq[Manga]] = mangasDbConfigProvider.run {
        val franchiseLike = s"%$franchise%"

        val query = for {
            manga <- MangaEntity.table
            mangaFranchise <- MangaFranchiseEntity.table if manga.id === mangaFranchise.mangaId
            franchise <- FranchiseEntity.table if (franchise.id === mangaFranchise.franchiseId) && (franchise.name.toLowerCase like franchiseLike)
        } yield manga

        query.distinct.result
    }

    def findAllByGenres(genres: Seq[String]): Future[Seq[Manga]] = mangasDbConfigProvider.run {
        val genresLowerCase = genres.map(_.toLowerCase)

        val query = for {
            manga <- MangaEntity.table
            mangaGenre <- MangaGenreEntity.table if manga.id === mangaGenre.mangaId
            genre <- GenreEntity.table if genre.id === mangaGenre.genreId && genre.name.toLowerCase.inSet(genresLowerCase)
        } yield manga

        query.distinct.result
    }
}