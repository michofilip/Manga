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

    def findAllGroupByMangaId(): Future[Map[Int, Seq[Genre]]] = {
        genreRepository.findAllGroupByMangaId().map { mangaIdGenreEntities =>
            mangaIdGenreEntities.groupMap { case (mangaId, _) => mangaId } { case (_, genreEntity) => Genre.fromEntity(genreEntity) }
        }
    }

}
