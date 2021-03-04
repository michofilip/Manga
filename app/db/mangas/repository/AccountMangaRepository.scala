package db.mangas.repository

import db.mangas.MangasDbConfigProvider
import db.mangas.model.AccountEntity
import db.mangas.model.MangaTable.MangaEntity
import dto.AccountManga
import slick.jdbc.PostgresProfile.api._

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class AccountMangaRepository @Inject()(val mangasDbConfigProvider: MangasDbConfigProvider)(implicit ec: ExecutionContext) {

    def findAllByAccountWithMangas(accountId: Int): Future[Seq[(AccountManga, MangaEntity)]] = mangasDbConfigProvider.run {
        val accountMangas = AccountEntity.table
            .filter(account => account.id === accountId)
            .flatMap(account => account.accountMangas)

        val query = for {
            accountManga <- accountMangas
            manga <- accountManga.manga
        } yield (accountManga, manga)

        query.result
    }

}