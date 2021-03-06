package service

import db.mangas.model.MangaTable.MangaEntity
import db.mangas.repository.MangaRepository
import dto.{Manga, MangaDetails, MangaV2}
import utils.ExceptionUtils

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Success, Try}

@Singleton
class MangaService @Inject()(mangaRepository: MangaRepository,
                             chapterService: ChapterService,
                             genreService: GenreService,
                             franchiseService: FranchiseService)
                            (implicit ec: ExecutionContext) {

    //    def findAll(): Future[Seq[Manga]] = {
    //        mangaRepository.findAll()
    //            .map(Manga.fromEntities)
    //    }

    def findAll(): Future[Seq[MangaV2]] = {
        mangaRepository.findAll()
            .flatMap(convertToMangas)
    }

    def findById(mangaId: Int): Future[Try[MangaDetails]] = {
        mangaRepository.findById(mangaId).flatMap {
            case None =>
                ExceptionUtils.noSuchElementException(s"Manga id $mangaId not found!")

            case Some(manga) =>
                val data = for {
                    chapters <- chapterService.findAllByMangaId(mangaId)
                    franchises <- franchiseService.findAllByMangaId(mangaId)
                    genres <- genreService.findAllByMangaId(mangaId)
                    avgScores <- findAvgScoreGroupByMangaId()
                } yield (chapters, franchises, genres, avgScores)

                data.map { case (chapters, franchises, genres, avgScores) =>
                    Success {
                        MangaDetails(
                            manga = Manga.fromEntity(manga),
                            franchises = franchises,
                            genres = genres,
                            avgScore = avgScores.get(mangaId),
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

            val data = for {
                all <- eventualAll
                byTitle <- eventualByTitle
                byFranchise <- eventualByFranchise
                byIncludedGenres <- eventualByIncludedGenres
                byExcludedGenres <- eventualByExcludedGenres
            } yield (all.toSet, byTitle.toSet, byFranchise.toSet, byIncludedGenres.toSet, byExcludedGenres.toSet)

            data.map { case (all, byTitle, byFranchise, byIncludedGenres, byExcludedGenres) =>
                ((all & byTitle & byFranchise & byIncludedGenres) diff byExcludedGenres).toSeq
            }.map(Manga.fromEntities)
        }
    }

    private def convertToManga(manga: MangaEntity): Future[MangaV2] = {
        convertToMangas(Seq(manga)).map(mangas => mangas.head)
    }

    private def convertToMangas(mangas: Seq[MangaEntity]): Future[Seq[MangaV2]] = {
        val data = for {
            franchises <- franchiseService.findAllGroupByMangaId()
            avgScores <- findAvgScoreGroupByMangaId()
        } yield (franchises, avgScores)

        data.map { case (franchises, avgScores) =>
            mangas.map { manga =>
                MangaV2(
                    id = manga.id,
                    title = manga.title,
                    franchises = franchises.getOrElse(manga.id, Seq.empty),
                    genres = Seq.empty, // TODO implement genres
                    avgScore = avgScores.get(manga.id)
                )
            }
        }
    }

    private def findAvgScoreGroupByMangaId(): Future[Map[Int, Double]] = {
        mangaRepository.findAvgScoreGroupByMangaId().map { mangaIdAvgScores =>
            mangaIdAvgScores.collect { case (mangaId, Some(avgScore)) =>
                mangaId -> avgScore
            }
        }.map(mangaIdAvgScores => mangaIdAvgScores.toMap)
    }

}
