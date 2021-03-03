package dto

import play.api.libs.json.{Json, OWrites}

case class AccountDetails(account: Account, user: User, mangas: Seq[AccountMangaDetails])

object AccountDetails {
    implicit val writes: OWrites[AccountDetails] = Json.writes[AccountDetails]
}
