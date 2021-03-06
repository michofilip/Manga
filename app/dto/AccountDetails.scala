package dto

import play.api.libs.json.{Json, OWrites}

case class AccountDetails(account: Account, user: User, tags: Seq[Tag], accountMangas: Seq[AccountManga])

object AccountDetails {
    implicit val writes: OWrites[AccountDetails] = Json.writes[AccountDetails]
}
