package db.mangas.model

import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

class MangaAvgScoreView(t: Tag) extends Table[(Int, Int, Int, Int, Int, Option[Double])](t, "v_manga_avg_score") {

    def mangaId = column[Int]("manga_id")

    def collections = column[Int]("collections")

    def reads = column[Int]("reads")

    def favorites = column[Int]("favorites")

    def votes = column[Int]("votes")

    def avgScore = column[Option[Double]]("avg_score")

    def * = (mangaId, collections, reads, favorites, votes, avgScore)

}

object MangaAvgScoreView {
    val all = TableQuery[MangaAvgScoreView]
}
