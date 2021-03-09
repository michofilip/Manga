package service

import db.mangas.model.MangaTable.MangaEntity
import db.mangas.repository.MangaRepository
import dto.{Manga, MangaWithChapters}
import utils.ExceptionUtils

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Success, Try}

@Singleton
class MangaService @Inject()(mangaRepository: MangaRepository,
                             chapterService: ChapterService,
                             genreService: GenreService,
                             franchiseService: FranchiseService,
                             mangaAvgScoreService: MangaAvgScoreService)
                            (implicit ec: ExecutionContext) {

    def findAll(): Future[Seq[Manga]] = {
        mangaRepository.findAll()
            .flatMap(convertToMangas)
    }

    def findById(mangaId: Int): Future[Try[MangaWithChapters]] = {
        mangaRepository.findById(mangaId).flatMap {
            case None =>
                ExceptionUtils.noSuchElementException(s"Manga id $mangaId not found!")

            case Some(mangaEntity) =>
                val data = for {
                    manga <- convertToManga(mangaEntity)
                    chapters <- chapterService.findAllByMangaId(mangaId)
                } yield (manga, chapters)

                data.map { case (manga, chapters) =>
                    Success {
                        MangaWithChapters(
                            manga = manga,
                            chapters = chapters
                        )
                    }
                }
        }
    }

    // TODO find out how to refactor this
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
            }.flatMap(convertToMangas)
        }
    }

    def convertToMangas(mangaEntities: Seq[MangaEntity]): Future[Seq[Manga]] = {
        val data = for {
            mangaIdToFranchises <- franchiseService.findAllGroupByMangaId()
            mangaIdToGenres <- genreService.findAllGroupByMangaId()
            mangaIdToAvgScores <- mangaAvgScoreService.findAvgScoreGroupByMangaId()
        } yield (mangaIdToFranchises, mangaIdToGenres, mangaIdToAvgScores)

        data.map { case (mangaIdToFranchises, mangaIdToGenres, mangaIdToAvgScores) =>
            mangaEntities.map { mangaEntity =>
                Manga(
                    id = mangaEntity.id,
                    title = mangaEntity.title,
                    franchises = mangaIdToFranchises.getOrElse(mangaEntity.id, Seq.empty),
                    genres = mangaIdToGenres.getOrElse(mangaEntity.id, Seq.empty),
                    avgScore = mangaIdToAvgScores.get(mangaEntity.id)
                )
            }
        }
    }

    private def convertToManga(mangaEntity: MangaEntity): Future[Manga] = {
        convertToMangas(Seq(mangaEntity)).map(mangas => mangas.head)
    }

}
