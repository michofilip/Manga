package service

import db.mangas.repository.MangaAvgScoreRepository

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class MangaAvgScoreService @Inject()(mangaAvgScoreRepository: MangaAvgScoreRepository)
                                    (implicit ec: ExecutionContext) {

    def findAllByIdInAvgScoreGroupByMangaId(mangaIds: Seq[Int]): Future[Map[Int, Double]] = {
        mangaAvgScoreRepository.findAllByIdIn(mangaIds)
            .map(mangaIdAvgScores => mangaIdAvgScores.toMap)
    }

}
