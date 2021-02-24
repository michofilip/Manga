package db.manga.repository

import db.manga.model.{Franchises, MangaFranchises, Mangas}
import dto.Franchise
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.db.NamedDatabase
import slick.jdbc.JdbcProfile
import slick.jdbc.PostgresProfile.api._

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class FranchiseRepository @Inject()(@NamedDatabase("mangas_db") val dbConfigProvider: DatabaseConfigProvider)
                                   (implicit ec: ExecutionContext)
    extends HasDatabaseConfigProvider[JdbcProfile] {

    def findAll(): Future[Seq[Franchise]] = db.run {
        Franchises.table.result
    }

    def findAllByMangaId(mangaId: Int): Future[Seq[Franchise]] = db.run {
        val query = for {
            manga <- Mangas.table if manga.id === mangaId
            mangaFranchise <- MangaFranchises.table if mangaFranchise.mangaId === manga.id
            franchise <- Franchises.table if franchise.id === mangaFranchise.franchiseId
        } yield franchise

        query.result
    }

    //    def findByName(name: String): Future[Option[Genre]] = db.run {
    //        Genres.table
    //            .filter(genre => genre.name === name)
    //            .result.headOption
    //    }
}