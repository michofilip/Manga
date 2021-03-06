package service

import db.mangas.repository.TagRepository
import dto.Tag

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class TagService @Inject()(tagRepository: TagRepository)
                          (implicit ec: ExecutionContext) {

    def findAllByAccountId(accountId: Int): Future[Seq[Tag]] = {
        tagRepository.findAllByAccountId(accountId)
            .map(Tag.fromEntities)
    }

    def findAllByAccountIdGroupByMangaId(accountId: Int): Future[Map[Int, Seq[Tag]]] = {
        tagRepository.findAllByAccountIdGroupByMangaId(accountId).map { mangaIdTagEntities =>
            mangaIdTagEntities.groupMap { case (mangaId, _) => mangaId } { case (_, tagEntity) => Tag.fromEntity(tagEntity) }
        }
    }

}
