package service

import db.mangas.repository.GenreRepository
import dto.Genre

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class GenreService @Inject()(genreRepository: GenreRepository)
                            (implicit ec: ExecutionContext) {

    def findAll(): Future[Seq[Genre]] = {
        genreRepository.findAll()
            .map(Genre.fromEntities)
    }

    @Deprecated
    def findAllGroupByMangaId(): Future[Map[Int, Seq[Genre]]] = {
        genreRepository.findAllGroupByMangaId().map { mangaIdGenreEntities =>
            mangaIdGenreEntities.groupMap { case (mangaId, _) => mangaId } { case (_, genreEntity) => Genre.fromEntity(genreEntity) }
        }
    }

    def findAllByIdInGroupByMangaId(mangaIds: Seq[Int]): Future[Map[Int, Seq[Genre]]] = {
        genreRepository.findAllByIdInGroupByMangaId(mangaIds).map { mangaIdGenreEntities =>
            mangaIdGenreEntities.groupMap { case (mangaId, _) => mangaId } { case (_, genreEntity) => Genre.fromEntity(genreEntity) }
        }
    }

}
