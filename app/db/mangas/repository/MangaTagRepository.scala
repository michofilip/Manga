package db.mangas.repository

import db.mangas.MangasDbConfigProvider
import db.mangas.model.MangaTagTable
import slick.jdbc.PostgresProfile.api._

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class MangaTagRepository @Inject()(mangasDbConfigProvider: MangasDbConfigProvider)(implicit ec: ExecutionContext) {

    def create(tagId: Int, mangaId: Int): Future[Int] = mangasDbConfigProvider.run {
        MangaTagTable.all += (mangaId, tagId)
    }

    def delete(tagId: Int, mangaId: Int): Future[Int] = mangasDbConfigProvider.run {
        MangaTagTable.all
            .filter(mangaTag => mangaTag.mangaId === mangaId && mangaTag.tagId === tagId)
            .delete
    }
}
