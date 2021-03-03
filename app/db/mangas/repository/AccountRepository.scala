package db.mangas.repository

import db.mangas.MangasDbConfigProvider
import db.mangas.model.AccountEntity
import dto.Account
import slick.jdbc.PostgresProfile.api._

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class AccountRepository @Inject()(val mangasDbConfigProvider: MangasDbConfigProvider)(implicit ec: ExecutionContext) {

    def findById(id: Int): Future[Option[Account]] = mangasDbConfigProvider.run {
        AccountEntity.table
            .filter(account => account.id === id)
            .result.headOption
    }
}