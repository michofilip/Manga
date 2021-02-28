package form

import play.api.libs.json.{Json, Reads}

case class UserForm(login: String, id: Option[Int])

object UserForm {
    implicit val reads: Reads[UserForm] = Json.reads[UserForm]
}
