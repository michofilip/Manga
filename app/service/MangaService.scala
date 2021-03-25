package service

import db.mangas.model.MangaTable.MangaEntity
import db.mangas.repository.MangaRepository
import dto.{Manga, MangaStatistics, MangaWithChapters}
import utils.FutureUtils

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Success, Try}

@Singleton
class MangaService @Inject()(mangaRepository: MangaRepository,
                             chapterService: ChapterService,
                             genreService: GenreService,
                             franchiseService: FranchiseService,
                             mangaStatisticsService: MangaStatisticsService)
                            (implicit ec: ExecutionContext) {

    def findAll(): Future[Seq[Manga]] = {
        mangaRepository.findAll()
            .flatMap(convertToMangas)
    }

    def findById(mangaId: Int): Future[Try[MangaWithChapters]] = {
        mangaRepository.findById(mangaId).flatMap {
            case None =>
                FutureUtils.noSuchElementException(s"Manga id $mangaId not found!")

            case Some(mangaEntity) =>
                for {
                    manga <- convertToManga(mangaEntity)
                    chapters <- chapterService.findAllByMangaId(mangaId)
                } yield {
                    Success {
                        MangaWithChapters(
                            manga = manga,
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
            val mangaEntities = for {
                includedMangaEntities <- mangaRepository.findAllByTitleAndFranchiseAndGenresIn(maybeTitle, maybeFranchise, includedGenres)
                excludedMangaEntities <- mangaRepository.findAllByGenres(excludedGenres)
            } yield {
                (includedMangaEntities.toSet -- excludedMangaEntities.toSet).toSeq
            }

            mangaEntities.flatMap(convertToMangas)
        }
    }

    def convertToMangas(mangaEntities: Seq[MangaEntity]): Future[Seq[Manga]] = {
        val mangaIds = mangaEntities.map(mangaEntity => mangaEntity.id)

        for {
            mangaIdToFranchises <- franchiseService.findAllByIdInGroupByMangaId(mangaIds)
            mangaIdToGenres <- genreService.findAllByIdInGroupByMangaId(mangaIds)
            mangaIdToStatistics <- mangaStatisticsService.findMangaStatisticsByIdInGroupByMangaId(mangaIds)
        } yield {
            mangaEntities.map { mangaEntity =>
                Manga(
                    id = mangaEntity.id,
                    title = mangaEntity.title,
                    franchises = mangaIdToFranchises.getOrElse(mangaEntity.id, Seq.empty),
                    genres = mangaIdToGenres.getOrElse(mangaEntity.id, Seq.empty),
                    statistics = mangaIdToStatistics.getOrElse(mangaEntity.id, MangaStatistics.defaultMangaStatistics)
                )
            }
        }
    }

    private def convertToManga(mangaEntity: MangaEntity): Future[Manga] = {
        convertToMangas(Seq(mangaEntity)).map(mangas => mangas.head)
    }

}
