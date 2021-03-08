package db.mangas.repository

import db.mangas.MangasDbConfigProvider
import db.mangas.model.MangaAvgScoreView
import slick.jdbc.PostgresProfile.api._

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class MangaAvgScoreRepository @Inject()(mangasDbConfigProvider: MangasDbConfigProvider)(implicit ec: ExecutionContext) {

    def findAll(): Future[Seq[(Int, Double)]] = mangasDbConfigProvider.run {
        MangaAvgScoreView.all.result
    }

    def findById(mangaId: Int): Future[Option[Double]] = mangasDbConfigProvider.run {
        MangaAvgScoreView.all
            .filter(mangaAvgScore => mangaAvgScore.mangaId === mangaId)
            .map(mangaAvgScore => mangaAvgScore.avgScore)
            .result.headOption
    }
}