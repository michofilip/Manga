package db.mangas.repository

import db.mangas.MangasDbConfigProvider
import db.mangas.model.MangaStatisticsView
import slick.jdbc.PostgresProfile.api._

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class MangaStatisticsRepository @Inject()(mangasDbConfigProvider: MangasDbConfigProvider)(implicit ec: ExecutionContext) {

    // TODO return entity
    def findAllByIdIn(mangaIds: Seq[Int]): Future[Seq[(Int, Int, Int, Int, Int, Option[Double])]] = mangasDbConfigProvider.run {
        MangaStatisticsView.all
            .filter(mangaAvgScore => mangaAvgScore.mangaId inSet mangaIds)
            .result
    }

}
