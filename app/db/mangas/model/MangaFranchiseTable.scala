package db.mangas.model

import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

class MangaFranchiseTable(tag: Tag) extends Table[(Int, Int)](tag, "manga_franchise") {

    def mangaId = column[Int]("manga_id")

    def franchiseId = column[Int]("franchise_id")

    def * = (mangaId, franchiseId)

    def mangaFk = foreignKey("manga_fk", mangaId, MangaTable.all)(_.id)

    def manga = mangaFk.filter(_.id === mangaId)

    def franchiseFk = foreignKey("franchise_fk", franchiseId, FranchiseTable.all)(_.id)

    def franchise = franchiseFk.filter(_.id === franchiseId)

}

object MangaFranchiseTable {
    val all = TableQuery[MangaFranchiseTable]
}
