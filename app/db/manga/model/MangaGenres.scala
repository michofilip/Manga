package db.manga.model

import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

class MangaGenres(tag: Tag) extends Table[(Int, Int)](tag, "manga_genres") {

    def mangaId = column[Int]("manga_id")

    def genreId = column[Int]("genre_id")

    def * = (mangaId, genreId)

    def manga = foreignKey("manga_fk", mangaId, Mangas.table)(_.id)

    def genre = foreignKey("genre_fk", genreId, Genres.table)(_.id)

}

object MangaGenres {
    val table = TableQuery[MangaGenres]
}




