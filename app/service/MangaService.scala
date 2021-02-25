package service

import db.manga.repository.{ChapterRepository, FranchiseRepository, GenreRepository, MangaRepository}
import dto.{Manga, MangaDetails}
import utils.ExceptionUtils

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class MangaService @Inject()(val mangaRepository: MangaRepository,
                             val chapterRepository: ChapterRepository,
                             val genreRepository: GenreRepository,
                             val franchiseRepository: FranchiseRepository)
                            (implicit ec: ExecutionContext) {

    def findAll(): Future[Seq[Manga]] = {
        mangaRepository.findAll()
    }

    def findById(mangaId: Int): Future[Either[Throwable, MangaDetails]] = {
        mangaRepository.findById(mangaId).flatMap {
            case None =>
                ExceptionUtils.noSuchElementException(s"Manga id $mangaId not found!")

            case Some(manga) =>
                val result = for {
                    chapters <- chapterRepository.findAllByMangaId(mangaId)
                    franchises <- franchiseRepository.findAllByMangaId(mangaId)
                    genres <- genreRepository.findAllByMangaId(mangaId)
                } yield (chapters, franchises, genres)

                result.map { case (chapters, franchises, genres) =>
                    Right {
                        MangaDetails(
                            manga = manga,
                            franchises = franchises,
                            genres = genres,
                            chapters = chapters
                        )
                    }
                }
        }
    }

    def findAllBySearchParameters(maybeTitle: Option[String],
                                  maybeFranchise: Option[String],
                                  includedGenres: Seq[String],
                                  excludedGenres: Seq[String]): Future[Seq[Manga]] = {
        if (maybeTitle.isEmpty && maybeFranchise.isEmpty && includedGenres.isEmpty && excludedGenres.isEmpty) {
            Future.successful {
                Seq.empty
            }
        } else {

            val eventualNone = Future(Seq.empty)
            val eventualAll = mangaRepository.findAll()
            val eventualByTitle = maybeTitle.fold(eventualAll)(mangaRepository.findAllByTitle)
            val eventualByFranchise = maybeFranchise.fold(eventualAll)(mangaRepository.findAllByFranchise)
            val eventualByIncludedGenres = if (includedGenres.isEmpty) eventualAll else mangaRepository.findAllByGenres(includedGenres)
            val eventualByExcludedGenres = if (excludedGenres.isEmpty) eventualNone else mangaRepository.findAllByGenres(excludedGenres)

            val result = for {
                all <- eventualAll
                byTitle <- eventualByTitle
                byFranchise <- eventualByFranchise
                byIncludedGenres <- eventualByIncludedGenres
                byExcludedGenres <- eventualByExcludedGenres
            } yield (all.toSet, byTitle.toSet, byFranchise.toSet, byIncludedGenres.toSet, byExcludedGenres.toSet)

            result.map { case (all, byTitle, byFranchise, byIncludedGenres, byExcludedGenres) =>
                ((all & byTitle & byFranchise & byIncludedGenres) diff byExcludedGenres).toSeq
            }

        }
    }

}
