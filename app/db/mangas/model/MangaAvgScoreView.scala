package db.mangas.model

import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

class MangaAvgScoreView(t: Tag) extends Table[(Int, Double)](t, "manga_avg_score") {

    def mangaId = column[Int]("manga_id")

    def avgScore = column[Double]("avg_score")

    def * = (mangaId, avgScore)

}

object MangaAvgScoreView {
    val all = TableQuery[MangaAvgScoreView]
}
