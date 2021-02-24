package db.manga.model

import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

class MangaFranchises(tag: Tag) extends Table[(Int, Int)](tag, "manga_franchises") {

    def mangaId = column[Int]("manga_id")

    def franchiseId = column[Int]("franchise_id")

    def * = (mangaId, franchiseId)

    def manga = foreignKey("manga_fk", mangaId, Mangas.table)(_.id)

    def franchise = foreignKey("franchise_fk", franchiseId, Franchises.table)(_.id)

}

object MangaFranchises {
    val table = TableQuery[MangaFranchises]
}






