package dto

import db.users.model.UserTable.UserEntity
import form.UserForm
import play.api.libs.json.{Json, OWrites}

case class User(login: String, id: Int = 0)

object User {
    implicit val writes: OWrites[User] = Json.writes[User]

    def fromEntity(userEntity: UserEntity): User = userEntity match {
        case UserEntity(login, id) => User(login, id)
    }

    def fromEntities(userEntities: Seq[UserEntity]): Seq[User] = {
        userEntities.map(fromEntity)
    }

    def toEntity(user: User): UserEntity = user match {
        case User(login, id) => UserEntity(login, id)
    }

    def from(userForm: UserForm): User = userForm match {
        case UserForm(login, id) => User(login, id.getOrElse(0))
    }
}
