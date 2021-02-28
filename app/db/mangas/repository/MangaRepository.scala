package db.mangas.repository

import db.mangas.MangasDbConfigProvider
import db.mangas.model.{FranchiseEntity, GenreEntity, MangaEntity}
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

        FranchiseEntity.table
            .filter(franchise => franchise.name.toLowerCase like franchiseLike)
            .flatMap(franchise => franchise.mangas)
            .distinct
            .result
    }

    def findAllByGenres(genres: Seq[String]): Future[Seq[Manga]] = mangasDbConfigProvider.run {
        val genresLowerCase = genres.map(_.toLowerCase)

        GenreEntity.table
            .filter(genre => genre.name.toLowerCase.inSet(genresLowerCase))
            .flatMap(genre => genre.mangas)
            .distinct
            .result
    }
}