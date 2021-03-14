package db.mangas.repository

import db.mangas.MangasDbConfigProvider
import db.mangas.model.TagTable.TagEntity
import db.mangas.model.{AccountTable, TagTable}
import slick.jdbc.PostgresProfile.api._

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class TagRepository @Inject()(mangasDbConfigProvider: MangasDbConfigProvider)(implicit ec: ExecutionContext) {

    def findAllByAccountId(accountId: Int): Future[Seq[TagEntity]] = mangasDbConfigProvider.run {
        AccountTable.all
            .filter(account => account.id === accountId)
            .flatMap(account => account.tags)
            .result
    }

    def findAllByAccountIdGroupByMangaId(accountId: Int): Future[Seq[(Int, TagEntity)]] = mangasDbConfigProvider.run {
        AccountTable.all
            .filter(account => account.id === accountId)
            .flatMap(account => account.accountMangas)
            .flatMap { accountManga =>
                accountManga.tags.map(tag => accountManga.mangaId -> tag)
            }.result
    }

    def create(tag: TagEntity): Future[TagEntity] = mangasDbConfigProvider.run {
        (TagTable.all returning TagTable.all.map(tag => tag.id) into ((tag, id) => tag.copy(id = id))) += tag
    }

    def update(tag: TagEntity): Future[Int] = mangasDbConfigProvider.run {
        val id = tag.id

        TagTable.all
            .filter(tag => tag.id === id)
            .update(tag)
    }

    def delete(id: Int): Future[Int] = mangasDbConfigProvider.run {
        TagTable.all
            .filter(tag => tag.id === id)
            .delete
    }
}
