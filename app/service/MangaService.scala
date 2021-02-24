package service

import db.manga.repository.{ChapterRepository, FranchiseRepository, GenreRepository, MangaRepository}
import dto.{Manga, MangaDetails}

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class MangaService @Inject()(val mangaRepository: MangaRepository,
                             val chapterRepository: ChapterRepository,
                             val genreRepository: GenreRepository,
                             val franchiseRepository: FranchiseRepository)
                            (implicit ec: ExecutionContext) {

    def findAll(): Future[Seq[Manga]] = {
        mangaRepository.findAll()
    }

    def findById(mangaId: Int): Future[Either[Throwable, MangaDetails]] = {
        mangaRepository.findById(mangaId).flatMap {
            case None =>
                Future.successful {
                    Left {
                        new NoSuchElementException(s"Manga id $mangaId not found!")
                    }
                }

            case Some(manga) =>
                val result = for {
                    chapters <- chapterRepository.findAllByMangaId(mangaId)
                    franchises <- franchiseRepository.findAllByMangaId(mangaId)
                    genres <- genreRepository.findAllByMangaId(mangaId)
                } yield (chapters, franchises, genres)

                result.map { case (chapters, franchises, genres) =>
                    Right {
                        MangaDetails(
                            manga = manga,
                            franchises = franchises,
                            genres = genres,
                            chapters = chapters
                        )
                    }
                }
        }
    }

}
