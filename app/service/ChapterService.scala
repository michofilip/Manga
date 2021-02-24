package service

import db.manga.repository.{ChapterRepository, PageRepository}
import dto.{Chapter, ChapterDetails}

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ChapterService @Inject()(val chapterRepository: ChapterRepository,
                               val pageRepository: PageRepository)
                              (implicit ec: ExecutionContext) {

    def findById(chapterId: Int): Future[Either[Throwable, ChapterDetails]] = {
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
                Future.successful {
                    Left {
                        new NoSuchElementException(s"Chapter id $chapterId not found!")
                    }
                }

            case Some(chapter) =>
                val result = for {
                    chapters <- chapterRepository.findAllByMangaId(chapter.mangaId)
                    pages <- pageRepository.findByChapterId(chapterId)
                } yield (chapters, pages)

                result.map { case (chapters, pages) =>
                    val (maybePreviousChapter, maybeNextChapter) = extractPreviousAndNextChapter(chapterId, chapters)

                    Right {
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
