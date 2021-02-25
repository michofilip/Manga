package db.manga.repository

import db.manga.MangasDbConfigProvider
import db.manga.model.{FranchiseEntity, MangaFranchiseEntity, MangaEntity}
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
        val query = for {
            manga <- MangaEntity.table if manga.id === mangaId
            mangaFranchise <- MangaFranchiseEntity.table if mangaFranchise.mangaId === manga.id
            franchise <- FranchiseEntity.table if franchise.id === mangaFranchise.franchiseId
        } yield franchise

        query.result
    }

    //    def findByName(name: String): Future[Option[Genre]] = db.run {
    //        Genres.table
    //            .filter(genre => genre.name === name)
    //            .result.headOption
    //    }
}