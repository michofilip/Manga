package db.mangas.repository

import db.mangas.MangasDbConfigProvider
import db.mangas.model.FranchiseTable.FranchiseEntity
import db.mangas.model.{FranchiseTable, MangaTable}
import slick.jdbc.PostgresProfile.api._

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class FranchiseRepository @Inject()(mangasDbConfigProvider: MangasDbConfigProvider)(implicit ec: ExecutionContext) {

    def findAll(): Future[Seq[FranchiseEntity]] = mangasDbConfigProvider.run {
        FranchiseTable.all.result
    }

    def findAllGroupByMangaId(): Future[Seq[(Int, FranchiseEntity)]] = mangasDbConfigProvider.run {
        MangaTable.all
            .flatMap { manga =>
                manga.franchises.map(franchise => manga.id -> franchise)
            }.result
    }

}
