package dto

import play.api.libs.json.{Json, OWrites}

case class MangaStatistics(collections: Int, read: Int, favorites: Int, votes: Int, avgScore: Option[Double])

object MangaStatistics {
    implicit val writes: OWrites[MangaStatistics] = Json.writes[MangaStatistics]

    def defaultMangaStatistics: MangaStatistics = MangaStatistics(0, 0, 0, 0, None)
}
