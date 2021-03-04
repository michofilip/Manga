package service

import db.mangas.repository.AccountMangaRepository
import dto.{AccountMangaDetails, Manga}

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class AccountMangaService @Inject()(accountMangaRepository: AccountMangaRepository)
                                   (implicit ec: ExecutionContext) {

    def findAllByAccount(accountId: Int): Future[Seq[AccountMangaDetails]] = {
        accountMangaRepository.findAllByAccountWithMangas(accountId).map { accountMangas =>
            accountMangas.map { case (accountManga, manga) =>
                AccountMangaDetails(
                    manga = Manga.fromEntity(manga),
                    isInCollection = accountManga.isInCollection,
                    isRead = accountManga.isRead,
                    isFavorite = accountManga.isFavorite,
                    score = accountManga.score
                )
            }
        }
    }

}
