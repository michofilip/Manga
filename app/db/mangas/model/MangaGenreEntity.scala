package db.mangas.model

import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

class MangaGenreEntity(tag: Tag) extends Table[(Int, Int)](tag, "manga_genre") {

    def mangaId = column[Int]("manga_id")

    def genreId = column[Int]("genre_id")

    def * = (mangaId, genreId)

    def mangaFk = foreignKey("manga_fk", mangaId, MangaTable.all)(_.id)

    def manga = mangaFk.filter(_.id === mangaId)

    def genreFk = foreignKey("genre_fk", genreId, GenreTable.all)(_.id)

    def genre = genreFk.filter(_.id === genreId)

}

object MangaGenreEntity {
    val table = TableQuery[MangaGenreEntity]
}
