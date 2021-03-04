package service

import db.mangas.repository.PageRepository
import dto.Page

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class PageService @Inject()(pageRepository: PageRepository)
                           (implicit ec: ExecutionContext) {

    def findByChapterId(chapterId: Int): Future[Seq[Page]] = {
        pageRepository.findByChapterId(chapterId)
            .map(Page.fromEntities)
    }

}
