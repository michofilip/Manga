package db.manga.repository

import db.manga.MangasDbConfigProvider
import db.manga.model.{FranchiseEntity, MangaEntity}
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
        MangaEntity.table
            .filter(manga => manga.id === mangaId)
            .flatMap(manga => manga.franchises)
            .result
    }

}