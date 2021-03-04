package db.mangas.repository

import db.mangas.MangasDbConfigProvider
import db.mangas.model.AccountTable
import db.mangas.model.AccountTable.AccountEntity
import slick.jdbc.PostgresProfile.api._

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class AccountRepository @Inject()(mangasDbConfigProvider: MangasDbConfigProvider)(implicit ec: ExecutionContext) {

    def findById(id: Int): Future[Option[AccountEntity]] = mangasDbConfigProvider.run {
        AccountTable.all
            .filter(account => account.id === id)
            .result.headOption
    }
}
