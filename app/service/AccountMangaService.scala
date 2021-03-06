package service

import db.mangas.repository.AccountMangaRepository
import dto.{AccountManga, Manga}

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class AccountMangaService @Inject()(accountMangaRepository: AccountMangaRepository,
                                    tagService: TagService)
                                   (implicit ec: ExecutionContext) {

    def findAllByAccount(accountId: Int): Future[Seq[AccountManga]] = {
        val data = for {
            accountMangas <- accountMangaRepository.findAllByAccountWithMangas(accountId)
            mangaIdToTags <- tagService.findAllByAccountIdGroupByMangaId(accountId)
        } yield (accountMangas, mangaIdToTags)

        data.map { case (accountMangas, mangaIdToTags) =>
            accountMangas.map { case (accountManga, manga) =>
                AccountManga.fromEntity(accountManga, Manga.fromEntity(manga), mangaIdToTags.getOrElse(manga.id, Seq.empty))
            }
        }
    }

}
