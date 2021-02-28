package dto

import form.UserForm
import play.api.libs.json.{Json, OWrites}

case class User(login: String, id: Int = 0)

object User {
    implicit val writes: OWrites[User] = Json.writes[User]

    def tupled = (User.apply _).tupled

    def from(userForm: UserForm): User = userForm match {
        case UserForm(login, Some(id)) => User(login, id)
        case UserForm(login, None) => User(login, 0)
    }
}
