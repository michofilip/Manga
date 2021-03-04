package db.files.model

import db.files.model.FileTable.FileEntity
import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

class FileTable(t: Tag) extends Table[FileEntity](t, "file") {

    def key = column[String]("key", O.PrimaryKey)

    def fileName = column[String]("file_name")

    def content = column[Array[Byte]]("content")

    def contentLength = column[Long]("content_length")

    def contentType = column[String]("content_type")

    def * = (fileName, content, contentLength, contentType, key).mapTo[FileEntity]

}

object FileTable {
    val all = TableQuery[FileTable]

    case class FileEntity(fileName: String, content: Array[Byte], contentLength: Long, contentType: String, key: String)

}
