package service

import db.mangas.repository.FranchiseRepository
import dto.Franchise

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class FranchiseService @Inject()(franchiseRepository: FranchiseRepository)
                                (implicit ec: ExecutionContext) {

    // TODO create controller
    def findAll(): Future[Seq[Franchise]] = {
        franchiseRepository.findAll()
            .map(Franchise.fromEntities)
    }

    def findAllGroupByMangaId(): Future[Map[Int, Seq[Franchise]]] = {
        franchiseRepository.findAllGroupByMangaId().map { mangaIdFranchiseEntities =>
            mangaIdFranchiseEntities.groupMap { case (mangaId, _) => mangaId } { case (_, franchiseEntity) => Franchise.fromEntity(franchiseEntity) }
        }
    }

}
