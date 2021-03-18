package db.mangas.repository

import db.mangas.MangasDbConfigProvider
import db.mangas.model.MangaAvgScoreView
import slick.jdbc.PostgresProfile.api._

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class MangaAvgScoreRepository @Inject()(mangasDbConfigProvider: MangasDbConfigProvider)(implicit ec: ExecutionContext) {

    def findAllByIdIn(mangaIds: Seq[Int]): Future[Seq[(Int, Double)]] = mangasDbConfigProvider.run {
        MangaAvgScoreView.all
            .filter(mangaAvgScore => mangaAvgScore.mangaId inSet mangaIds)
            .result
    }

}
