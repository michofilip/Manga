package service

import db.mangas.repository.TagRepository
import dto.Tag
import form.TagForm
import utils.FutureUtils

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Success, Try}

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

    def create(tagForm: TagForm): Future[Try[Tag]] = {
        val tagEntity = Tag.toEntity(tagForm)

        tagRepository.create(tagEntity).map { tagEntity =>
            Success {
                Tag.fromEntity(tagEntity)
            }
        }.recoverWith { case exception =>
            FutureUtils.futureFailure(exception)
        }
    }

    def update(tagForm: TagForm): Future[Try[Tag]] = {
        val tagEntity = Tag.toEntity(tagForm)

        tagRepository.update(tagEntity).flatMap {
            case 0 =>
                FutureUtils.noSuchElementException(s"Tag id ${tagEntity.id} not found!")

            case _ =>
                FutureUtils.futureSuccess(Tag.fromEntity(tagEntity))
        }
    }

    def delete(tagId: Int): Future[Try[Unit]] = {
        tagRepository.delete(tagId).flatMap {
            case 0 =>
                FutureUtils.noSuchElementException(s"Tag id $tagId not found!")

            case _ =>
                FutureUtils.futureSuccess
        }
    }

}
