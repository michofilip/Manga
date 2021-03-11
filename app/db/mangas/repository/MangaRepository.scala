package db.mangas.repository

import db.mangas.MangasDbConfigProvider
import db.mangas.model.MangaTable.MangaEntity
import db.mangas.model.{GenreTable, MangaTable}
import slick.jdbc.PostgresProfile.api._

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class MangaRepository @Inject()(mangasDbConfigProvider: MangasDbConfigProvider)(implicit ec: ExecutionContext) {

    def findAll(): Future[Seq[MangaEntity]] = mangasDbConfigProvider.run {
        MangaTable.all.result
    }

    def findById(id: Int): Future[Option[MangaEntity]] = mangasDbConfigProvider.run {
        MangaTable.all
            .filter(manga => manga.id === id)
            .result.headOption
    }

    def findAllByGenres(genres: Seq[String]): Future[Seq[MangaEntity]] = mangasDbConfigProvider.run {
        val genresLowerCase = genres.map(_.toLowerCase)

        GenreTable.all
            .filter(genre => genre.name.toLowerCase.inSet(genresLowerCase))
            .flatMap(genre => genre.mangas)
            .distinct
            .result
    }

    def findAllByTitleAndFranchiseAndGenresIn(maybeTitle: Option[String],
                                              maybeFranchise: Option[String],
                                              genres: Seq[String]): Future[Seq[MangaEntity]] = mangasDbConfigProvider.run {
        val maybeTitleLike = maybeTitle.map(title => s"%$title%")
        val maybeFranchiseLike = maybeFranchise.map(franchise => s"%$franchise%")
        val genresLowerCase = genres.map(_.toLowerCase)

        MangaTable.all
            .flatMap { manga =>
                manga.franchises.flatMap { franchise =>
                    manga.genres.map { genre =>
                        (manga, franchise, genre)
                    }
                }
            }
            .filter { case (manga, _, _) =>
                maybeTitleLike match {
                    case Some(titleLike) => manga.title.toLowerCase like titleLike
                    case None => true: Rep[Boolean]
                }
            }
            .filter { case (_, franchise, _) =>
                maybeFranchiseLike match {
                    case Some(franchiseLike) => franchise.name.toLowerCase like franchiseLike
                    case None => true: Rep[Boolean]
                }
            }
            .filter {
                case (_, _, genre) if genresLowerCase.nonEmpty => genre.name.toLowerCase.inSet(genresLowerCase)
                case _ => true: Rep[Boolean]
            }
            .map { case (manga, _, _) => manga }
            .distinct
            .result
    }

}
