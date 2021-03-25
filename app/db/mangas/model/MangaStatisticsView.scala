package db.mangas.model

import db.mangas.model.MangaStatisticsView.MangaStatisticsEntity
import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

class MangaStatisticsView(t: Tag) extends Table[MangaStatisticsEntity](t, "v_manga_statistics") {

    def mangaId = column[Int]("manga_id")

    def collections = column[Int]("collections")

    def reads = column[Int]("reads")

    def favorites = column[Int]("favorites")

    def votes = column[Int]("votes")

    def avgScore = column[Option[Double]]("avg_score")

    def * = (mangaId, collections, reads, favorites, votes, avgScore).mapTo[MangaStatisticsEntity]

}

object MangaStatisticsView {
    val all = TableQuery[MangaStatisticsView]

    case class MangaStatisticsEntity(mangaId: Int, collections: Int, reads: Int, favorites: Int, votes: Int, avgScore: Option[Double])

}
