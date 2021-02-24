package form

import play.api.libs.json.{Json, Reads}

case class GenresSearchForm(includeGenres: Seq[String], excludeGenres: Seq[String])

object GenresSearchForm {
    implicit val reads: Reads[GenresSearchForm] = Json.reads[GenresSearchForm]
}
