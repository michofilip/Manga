package service

import db.mangas.model.ChapterTable.ChapterEntity
import db.mangas.repository.ChapterRepository
import dto.{Chapter, ChapterDetails}
import utils.ExceptionUtils

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
        def extractPreviousAndNextChapter(chapterId: Int, chapters: Seq[ChapterEntity]): (Option[ChapterEntity], Option[ChapterEntity]) = {
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
                    pages <- pageService.findByChapterId(chapterId)
                } yield (chapters, pages)

                result.map { case (chapters, pages) =>
                    val (previousChapter, nextChapter) = extractPreviousAndNextChapter(chapterId, chapters)

                    Success {
                        ChapterDetails.fromEntity(chapter, previousChapter, nextChapter, pages)
                    }
                }
        }
    }

}
