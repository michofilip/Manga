package service

import db.mangas.repository.MangaStatisticsRepository
import dto.MangaStatistics

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class MangaStatisticsService @Inject()(mangaStatisticsRepository: MangaStatisticsRepository)
                                      (implicit ec: ExecutionContext) {

    def findMangaStatisticsByIdInGroupByMangaId(mangaIds: Seq[Int]): Future[Map[Int, MangaStatistics]] = {
        mangaStatisticsRepository.findAllByIdIn(mangaIds).map { mangaIdStatistics =>
            mangaIdStatistics.map { mangaStatisticsEntity =>
                mangaStatisticsEntity.mangaId -> MangaStatistics.fromEntity(mangaStatisticsEntity)
            }.toMap
        }
    }

}
