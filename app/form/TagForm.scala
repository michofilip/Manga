package form

import play.api.libs.json.{Json, Reads}

case class TagForm(id: Option[Int], accountId: Int, tag: String)

object TagForm {
    implicit val reads: Reads[TagForm] = Json.reads[TagForm]
}
