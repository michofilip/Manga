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

    def findAllByIdInGroupByMangaId(mangaIds: Seq[Int]): Future[Map[Int, Seq[Franchise]]] = {
        franchiseRepository.findAllByIdInGroupByMangaId(mangaIds).map { mangaIdFranchiseEntities =>
            mangaIdFranchiseEntities.groupMap { case (mangaId, _) => mangaId } { case (_, franchiseEntity) => Franchise.fromEntity(franchiseEntity) }
        }
    }

}
