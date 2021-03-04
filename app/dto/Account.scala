package dto

import db.mangas.model.AccountTable.AccountEntity
import play.api.libs.json.{Json, OWrites}

case class Account(userId: Int, isActive: Boolean, id: Int = 0)

object Account {
    implicit val writes: OWrites[Account] = Json.writes[Account]

    def fromEntity(accountEntity: AccountEntity): Account = accountEntity match {
        case AccountEntity(userId, isActive, id) => Account(userId, isActive, id)
    }
}
