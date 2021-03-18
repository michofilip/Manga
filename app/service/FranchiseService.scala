package service

import db.mangas.repository.FranchiseRepository
import dto.Franchise

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class FranchiseService @Inject()(franchiseRepository: FranchiseRepository)
                                (implicit ec: ExecutionContext) {

    def findAll(): Future[Seq[Franchise]] = {
        franchiseRepository.findAll()
            .map(Franchise.fromEntities)
    }

    @Deprecated
    def findAllGroupByMangaId(): Future[Map[Int, Seq[Franchise]]] = {
        franchiseRepository.findAllGroupByMangaId().map { mangaIdFranchiseEntities =>
            mangaIdFranchiseEntities.groupMap { case (mangaId, _) => mangaId } { case (_, franchiseEntity) => Franchise.fromEntity(franchiseEntity) }
        }
    }

    def findAllGroupByMangaId(mangaIds: Seq[Int]): Future[Map[Int, Seq[Franchise]]] = {
        franchiseRepository.findAllGroupByMangaId(mangaIds).map { mangaIdFranchiseEntities =>
            mangaIdFranchiseEntities.groupMap { case (mangaId, _) => mangaId } { case (_, franchiseEntity) => Franchise.fromEntity(franchiseEntity) }
        }
    }

}
