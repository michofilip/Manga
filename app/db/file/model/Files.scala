package db.file.model

import dto.File
import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

class Files(tag: Tag) extends Table[File](tag, "files") {

    def key = column[String]("key", O.PrimaryKey)

    def name = column[String]("name")

    def size = column[Long]("size")

    def content = column[Array[Byte]]("content")

    def * = (name, size, content, key).mapTo[File]

}

object Files {
    val table = TableQuery[Files]
}