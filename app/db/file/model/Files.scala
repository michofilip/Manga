package db.file.model

import dto.File
import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

class Files(tag: Tag) extends Table[File](tag, "files") {

    def key = column[String]("key", O.PrimaryKey)

    def fileName = column[String]("file_name")

    def content = column[Array[Byte]]("content")

    def contentLength = column[Long]("content_length")

    def contentType = column[String]("content_type")

    def * = (fileName, content, contentLength, contentType, key).mapTo[File]

}

object Files {
    val table = TableQuery[Files]
}