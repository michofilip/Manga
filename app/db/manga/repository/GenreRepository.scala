package db.manga.repository

import db.manga.MangasDbConfigProvider
import db.manga.model.{GenreEntity, MangaGenreEntity, MangaEntity}
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
        MangaEntity.table
            .filter(manga => manga.id === mangaId)
            .flatMap(manga => manga.genres)
            .result
    }
}