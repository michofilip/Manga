package db.mangas.repository

import db.mangas.MangasDbConfigProvider
import db.mangas.model.AccountTable
import db.mangas.model.TagTable.TagEntity
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
}
