package service

import db.mangas.repository.{ChapterRepository, PageRepository}
import dto.{Chapter, ChapterDetails}
import utils.ExceptionUtils

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Success, Try}

@Singleton
class ChapterService @Inject()(val chapterRepository: ChapterRepository,
                               val pageRepository: PageRepository)
                              (implicit ec: ExecutionContext) {

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
                ExceptionUtils.noSuchElementException(s"Chapter id $chapterId not found!")

            case Some(chapter) =>
                val result = for {
                    chapters <- chapterRepository.findAllByMangaId(chapter.mangaId)
                    pages <- pageRepository.findByChapterId(chapterId)
                } yield (chapters, pages)

                result.map { case (chapters, pages) =>
                    val (maybePreviousChapter, maybeNextChapter) = extractPreviousAndNextChapter(chapterId, chapters)

                    Success {
                        ChapterDetails(
                            chapter = chapter,
                            previousChapter = maybePreviousChapter,
                            nextChapter = maybeNextChapter,
                            pages = pages
                        )
                    }
                }
        }
    }

}
