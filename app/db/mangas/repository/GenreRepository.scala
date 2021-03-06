package db.mangas.repository

import db.mangas.MangasDbConfigProvider
import db.mangas.model.GenreTable.GenreEntity
import db.mangas.model.{GenreTable, MangaTable}
import slick.jdbc.PostgresProfile.api._

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class GenreRepository @Inject()(mangasDbConfigProvider: MangasDbConfigProvider)(implicit ec: ExecutionContext) {

    def findAll(): Future[Seq[GenreEntity]] = mangasDbConfigProvider.run {
        GenreTable.all.result
    }

    def findAllGroupByMangaId(): Future[Seq[(Int, GenreEntity)]] = mangasDbConfigProvider.run {
        MangaTable.all
            .flatMap { manga =>
                manga.genres.map(genre => manga.id -> genre)
            }.result
    }
}