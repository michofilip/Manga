package service

import db.mangas.repository.ChapterRepository
import dto.{Chapter, ChapterDetails}
import utils.FutureUtils

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Success, Try}

@Singleton
class ChapterService @Inject()(chapterRepository: ChapterRepository,
                               pageService: PageService)
                              (implicit ec: ExecutionContext) {

    def findAllByMangaId(mangaId: Int): Future[Seq[Chapter]] = {
        chapterRepository.findAllByMangaId(mangaId)
            .map(Chapter.fromEntities)
    }

    def findById(chapterId: Int): Future[Try[ChapterDetails]] = {
        def extractPreviousAndNextChapter(chapterId: Int, chapters: Seq[Chapter]): (Option[Chapter], Option[Chapter]) = {
            val maybePreviousChapter = chapters
                .takeWhile(chapter => chapter.id != chapterId)
                .lastOption

            val maybeNextChapter = chapters
                .reverse
                .takeWhile(chapter => chapter.id != chapterId)
                .lastOption

            (maybePreviousChapter, maybeNextChapter)
        }

        chapterRepository.findById(chapterId).flatMap {
            case None =>
                FutureUtils.noSuchElementException(s"Chapter id $chapterId not found!")

            case Some(chapterEntity) =>
                for {
                    chapters <- findAllByMangaId(chapterEntity.mangaId)
                    pages <- pageService.findByChapterId(chapterId)
                } yield {
                    val (previousChapter, nextChapter) = extractPreviousAndNextChapter(chapterId, chapters)

                    Success {
                        ChapterDetails(
                            chapter = Chapter.fromEntity(chapterEntity),
                            previousChapter = previousChapter,
                            nextChapter = nextChapter,
                            pages = pages
                        )
                    }
                }
        }
    }

}
