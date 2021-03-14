package dto

import db.users.model.UserTable.UserEntity
import form.UserForm
import play.api.libs.json.{Json, OWrites}

case class User(id: Int, login: String)

object User {
    implicit val writes: OWrites[User] = Json.writes[User]

    def fromEntity(userEntity: UserEntity): User = {
        User(
            id = userEntity.id,
            login = userEntity.login
        )
    }

    def fromEntities(userEntities: Seq[UserEntity]): Seq[User] = {
        userEntities.map(fromEntity)
    }

    def toEntity(user: User): UserEntity = {
        UserEntity(
            id = user.id,
            login = user.login
        )
    }

    // TODO convert directly to entity
    def from(userForm: UserForm): User = {
        User(
            id = userForm.id.getOrElse(0),
            login = userForm.login
        )
    }
}
