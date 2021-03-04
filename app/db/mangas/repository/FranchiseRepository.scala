package db.mangas.repository

import db.mangas.MangasDbConfigProvider
import db.mangas.model.{FranchiseEntity, MangaTable}
import dto.Franchise
import slick.jdbc.PostgresProfile.api._

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class FranchiseRepository @Inject()(val mangasDbConfigProvider: MangasDbConfigProvider)(implicit ec: ExecutionContext) {

    def findAll(): Future[Seq[Franchise]] = mangasDbConfigProvider.run {
        FranchiseEntity.table.result
    }

    def findAllByMangaId(mangaId: Int): Future[Seq[Franchise]] = mangasDbConfigProvider.run {
        MangaTable.all
            .filter(manga => manga.id === mangaId)
            .flatMap(manga => manga.franchises)
            .result
    }

}