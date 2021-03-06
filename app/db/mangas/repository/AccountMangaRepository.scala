package db.mangas.repository

import db.mangas.MangasDbConfigProvider
import db.mangas.model.AccountMangaTable.AccountMangaEntity
import db.mangas.model.AccountTable
import db.mangas.model.MangaTable.MangaEntity
import slick.jdbc.PostgresProfile.api._

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class AccountMangaRepository @Inject()(mangasDbConfigProvider: MangasDbConfigProvider)(implicit ec: ExecutionContext) {

    def findAllWithMangasByAccountId(accountId: Int): Future[Seq[(AccountMangaEntity, MangaEntity)]] = mangasDbConfigProvider.run {
        AccountTable.all
            .filter(account => account.id === accountId)
            .flatMap(account => account.accountMangas)
            .flatMap { accountManga =>
                accountManga.manga.map(manga => accountManga -> manga)
            }.result
    }

}
