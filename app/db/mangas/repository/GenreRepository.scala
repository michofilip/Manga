package db.mangas.repository

import db.mangas.MangasDbConfigProvider
import db.mangas.model.{GenreEntity, MangaGenreEntity, MangaTable}
import dto.Genre
import slick.jdbc.PostgresProfile.api._

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class GenreRepository @Inject()(val mangasDbConfigProvider: MangasDbConfigProvider)(implicit ec: ExecutionContext) {

    def findAll(): Future[Seq[Genre]] = mangasDbConfigProvider.run {
        GenreEntity.table.result
    }

    def findAllByMangaId(mangaId: Int): Future[Seq[Genre]] = mangasDbConfigProvider.run {
        MangaTable.all
            .filter(manga => manga.id === mangaId)
            .flatMap(manga => manga.genres)
            .result
    }
}