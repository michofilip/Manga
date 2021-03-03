package dto

import play.api.libs.json.{Json, OWrites}

case class AccountMangaDetails(manga: Manga, isInCollection: Boolean, isRead: Boolean, isFavorite: Boolean, score: Option[Int])

object AccountMangaDetails {
    implicit val writes: OWrites[AccountMangaDetails] = Json.writes[AccountMangaDetails]
}
