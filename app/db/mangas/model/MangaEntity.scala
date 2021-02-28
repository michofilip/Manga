package db.mangas.model

import dto.Manga
import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

class MangaEntity(tag: Tag) extends Table[Manga](tag, "manga") {

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def title = column[String]("title")

    def * = (title, id).mapTo[Manga]

    def chapters = ChapterEntity.table.filter(_.mangaId === id)

    def genres = MangaGenreEntity.table.filter(_.mangaId === id).flatMap(_.genre)

    def franchises = MangaFranchiseEntity.table.filter(_.mangaId === id).flatMap(_.franchise)

}

object MangaEntity {
    val table = TableQuery[MangaEntity]
}
