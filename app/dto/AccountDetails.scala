package dto

import play.api.libs.json.{Json, OWrites}

// TODO remove user
case class AccountDetails(account: AccountV2, tags: Seq[Tag], accountMangas: Seq[AccountManga])

object AccountDetails {
    implicit val writes: OWrites[AccountDetails] = Json.writes[AccountDetails]
}
