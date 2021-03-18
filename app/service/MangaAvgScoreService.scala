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

    @Deprecated
    def findAvgScoreGroupByMangaId(): Future[Map[Int, Double]] = {
        mangaAvgScoreRepository.findAll()
            .map(mangaIdAvgScores => mangaIdAvgScores.toMap)
    }

    def findAvgScoreGroupByMangaId(mangaIds: Seq[Int]): Future[Map[Int, Double]] = {
        mangaAvgScoreRepository.findAll(mangaIds)
            .map(mangaIdAvgScores => mangaIdAvgScores.toMap)
    }

}
