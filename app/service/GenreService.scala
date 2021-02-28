package service

import db.mangas.repository.GenreRepository
import dto.Genre

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class GenreService @Inject()(val genreRepository: GenreRepository)
                            (implicit ec: ExecutionContext) {

    def findAll(): Future[Seq[Genre]] = {
        genreRepository.findAll()
    }

}
