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
    def findAllByMangaId(mangaId: Int): Future[Seq[Franchise]] = {
        franchiseRepository.findAllByMangaId(mangaId)
            .map(Franchise.fromEntities)
    }

    def findAllGroupByMangaId(): Future[Map[Int, Seq[Franchise]]] = {
        franchiseRepository.findAllGroupByMangaId().map { mangaIdFranchises =>
            mangaIdFranchises.groupMap { case (mangaId, _) => mangaId } { case (_, franchise) => Franchise.fromEntity(franchise) }
        }
    }

}
