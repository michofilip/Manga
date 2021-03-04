package service

import db.mangas.repository.FranchiseRepository
import dto.Franchise

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class FranchiseService @Inject()(franchiseRepository: FranchiseRepository)
                                (implicit ec: ExecutionContext) {

    def findAllByMangaId(mangaId: Int): Future[Seq[Franchise]] = {
        franchiseRepository.findAllByMangaId(mangaId)
            .map(Franchise.fromEntities)
    }

}
