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
            accountMangaWithMangaEntities <- accountMangaRepository.findAllWithMangasByAccountId(accountId)
            (accountMangaEntities, mangaIdToManga) <- extractFromAccountMangasWithMangas(accountMangaWithMangaEntities)
            mangaIdToTags <- tagService.findAllByAccountIdGroupByMangaId(accountId)
        } yield (accountMangaEntities, mangaIdToManga, mangaIdToTags)

        data.map { case (accountMangaEntities, mangaIdToManga, mangaIdToTags) =>
            accountMangaEntities.map { accountMangaEntity =>
                AccountManga.fromEntity(accountMangaEntity, mangaIdToManga(accountMangaEntity.mangaId), mangaIdToTags.getOrElse(accountMangaEntity.mangaId, Seq.empty))
            }
        }
    }

    private def extractFromAccountMangasWithMangas(accountMangaWithMangaEntities: Seq[(AccountMangaEntity, MangaEntity)]): Future[(Seq[AccountMangaEntity], Map[Int, Manga])] = {
        val (accountMangaEntities, mangaEntities) = accountMangaWithMangaEntities.unzip

        val futureAccountMangaEntities = Future.successful(accountMangaEntities)
        val futureMangaIdToManga = mangaService.convertToMangas(mangaEntities).map { mangas =>
            mangas.map(manga => manga.id -> manga).toMap
        }

        futureAccountMangaEntities zip futureMangaIdToManga
    }

}
