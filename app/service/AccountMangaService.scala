package service

import db.mangas.model.AccountMangaTable.AccountMangaEntity
import db.mangas.model.MangaTable.MangaEntity
import db.mangas.repository.AccountMangaRepository
import dto.{AccountManga, Manga}

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class AccountMangaService @Inject()(accountMangaRepository: AccountMangaRepository,
                                    mangaService: MangaService,
                                    tagService: TagService)
                                   (implicit ec: ExecutionContext) {

    def findAllByAccount(accountId: Int): Future[Seq[AccountManga]] = {
        val data = for {
            accountMangasWithMangas <- accountMangaRepository.findAllWithMangasByAccountId(accountId)
            (accountMangas, mangaIdToManga) <- extractFromAccountMangasWithMangas(accountMangasWithMangas)
            mangaIdToTags <- tagService.findAllByAccountIdGroupByMangaId(accountId)
        } yield (accountMangas, mangaIdToManga, mangaIdToTags)

        data.map { case (accountMangas, mangaIdToManga, mangaIdToTags) =>
            accountMangas.map { accountManga =>
                AccountManga.fromEntity(accountManga, mangaIdToManga(accountManga.mangaId), mangaIdToTags.getOrElse(accountManga.mangaId, Seq.empty))
            }
        }
    }

    private def extractFromAccountMangasWithMangas(accountMangasWithMangas: Seq[(AccountMangaEntity, MangaEntity)]): Future[(Seq[AccountMangaEntity], Map[Int, Manga])] = {
        val (accountMangas, mangas) = accountMangasWithMangas.unzip

        val futureAccountMangas = Future.successful(accountMangas)
        val futureMangaIdToManga = mangaService.convertToMangas(mangas).map { mangas =>
            mangas.map(manga => manga.id -> manga).toMap
        }

        futureAccountMangas zip futureMangaIdToManga
    }

}
