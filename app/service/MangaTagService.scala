package service

import db.mangas.repository.MangaTagRepository
import utils.FutureUtils

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Success, Try}

@Singleton
class MangaTagService @Inject()(mangaTagRepository: MangaTagRepository)
                               (implicit ec: ExecutionContext) {

    def assignTag(tagId: Int, mangaId: Int): Future[Try[Unit]] = {
        mangaTagRepository.create(tagId, mangaId).flatMap { _ =>
            Future.successful {
                Success((): Unit)
            }
        }.recoverWith { case exception =>
            FutureUtils.futureFailure(exception)
        }
    }

    def unAssignTag(tagId: Int, mangaId: Int): Future[Try[Unit]] = {
        mangaTagRepository.delete(tagId, mangaId).flatMap {
            case 0 =>
                FutureUtils.noSuchElementException(s"Tag id $tagId is not assign to manga id $mangaId!")
            case _ =>
                FutureUtils.futureSuccess
        }.recoverWith { case exception =>
            FutureUtils.futureFailure(exception)
        }
    }

}
