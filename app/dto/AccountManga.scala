package dto

import play.api.libs.json.{Json, OWrites}

case class AccountManga(accountId: Int, mangaId: Int, isInCollection: Boolean, isRead: Boolean, isFavorite: Boolean, score: Option[Int])

object AccountManga {
    implicit val writes: OWrites[AccountManga] = Json.writes[AccountManga]

    def tupled = (AccountManga.apply _).tupled
}
