package db.mangas.model

import db.mangas.model.MangaTable.MangaEntity
import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

class MangaTable(tag: Tag) extends Table[MangaEntity](tag, "manga") {

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def title = column[String]("title")

    def * = (title, id).mapTo[MangaEntity]

    def chapters = ChapterTable.all.filter(_.mangaId === id)

    def genres = MangaGenreEntity.table.filter(_.mangaId === id).flatMap(_.genre)

    def franchises = MangaFranchiseTable.all.filter(_.mangaId === id).flatMap(_.franchise)

}

object MangaTable {
    val all = TableQuery[MangaTable]

    case class MangaEntity(title: String, id: Int = 0)

}
