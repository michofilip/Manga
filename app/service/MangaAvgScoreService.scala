package service

import db.mangas.repository.MangaAvgScoreRepository

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class MangaAvgScoreService @Inject()(mangaAvgScoreRepository: MangaAvgScoreRepository)
                                    (implicit ec: ExecutionContext) {

    def findAvgScore(mangaId: Int): Future[Option[Double]] = {
        mangaAvgScoreRepository.findById(mangaId)
    }

    def findAvgScoreGroupByMangaId(): Future[Map[Int, Double]] = {
        mangaAvgScoreRepository.findAll().map { mangaIdAvgScores =>
            mangaIdAvgScores.collect { case (mangaId, avgScore) =>
                mangaId -> avgScore
            }.toMap
        }
    }

}
