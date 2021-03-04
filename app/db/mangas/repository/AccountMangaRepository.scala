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

    def findAllByAccountWithMangas(accountId: Int): Future[Seq[(AccountMangaEntity, MangaEntity)]] = mangasDbConfigProvider.run {
        val accountMangas = AccountTable.all
            .filter(account => account.id === accountId)
            .flatMap(account => account.accountMangas)

        val query = for {
            accountManga <- accountMangas
            manga <- accountManga.manga
        } yield (accountManga, manga)

        query.result
    }

}
