package db.manga.model

import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

class MangaFranchiseEntity(tag: Tag) extends Table[(Int, Int)](tag, "manga_franchise") {

    def mangaId = column[Int]("manga_id")

    def franchiseId = column[Int]("franchise_id")

    def * = (mangaId, franchiseId)

    def mangaFk = foreignKey("manga_fk", mangaId, MangaEntity.table)(_.id)

    def franchiseFk = foreignKey("franchise_fk", franchiseId, FranchiseEntity.table)(_.id)

}

object MangaFranchiseEntity {
    val table = TableQuery[MangaFranchiseEntity]
}
