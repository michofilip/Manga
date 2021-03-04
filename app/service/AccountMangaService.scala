package service

import db.mangas.repository.AccountMangaRepository
import dto.AccountManga

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class AccountMangaService @Inject()(accountMangaRepository: AccountMangaRepository)
                                   (implicit ec: ExecutionContext) {

    def findAllByAccount(accountId: Int): Future[Seq[AccountManga]] = {
        accountMangaRepository.findAllByAccountWithMangas(accountId).map { accountMangas =>
            accountMangas.map { case (accountManga, manga) =>
                AccountManga.fromEntity(accountManga, manga)
            }
        }
    }

}
