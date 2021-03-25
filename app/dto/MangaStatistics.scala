package dto

import db.mangas.model.MangaStatisticsView.MangaStatisticsEntity
import play.api.libs.json.{Json, OWrites}

case class MangaStatistics(collections: Int, read: Int, favorites: Int, votes: Int, avgScore: Option[Double])

object MangaStatistics {
    implicit val writes: OWrites[MangaStatistics] = Json.writes[MangaStatistics]

    def defaultMangaStatistics: MangaStatistics = MangaStatistics(0, 0, 0, 0, None)

    def fromEntity(mangaStatisticsEntity: MangaStatisticsEntity): MangaStatistics = {
        MangaStatistics(
            collections = mangaStatisticsEntity.collections,
            read = mangaStatisticsEntity.reads,
            favorites = mangaStatisticsEntity.favorites,
            votes = mangaStatisticsEntity.votes,
            avgScore = mangaStatisticsEntity.avgScore
        )
    }
}
