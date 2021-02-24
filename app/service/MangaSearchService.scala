package service

import db.manga.repository.MangaRepository
import dto.Manga

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class MangaSearchService @Inject()(val mangaRepository: MangaRepository)
                                  (implicit ec: ExecutionContext) {

    def findAllBySearchParameters(maybeTitle: Option[String],
                                  maybeFranchise: Option[String],
                                  includedGenres: Seq[String],
                                  excludedGenres: Seq[String]): Future[Seq[Manga]] = {
        if (maybeTitle.isEmpty && maybeFranchise.isEmpty && includedGenres.isEmpty && excludedGenres.isEmpty) {
            Future.successful {
                Seq.empty
            }
        } else {

            val eventualNone = Future(Seq.empty)
            val eventualAll = mangaRepository.findAll()
            val eventualByTitle = maybeTitle.fold(eventualAll)(mangaRepository.findAllByTitle)
            val eventualByFranchise = maybeFranchise.fold(eventualAll)(mangaRepository.findAllByFranchise)
            val eventualByIncludedGenres = if (includedGenres.isEmpty) eventualAll else mangaRepository.findAllByGenres(includedGenres)
            val eventualByExcludedGenres = if (excludedGenres.isEmpty) eventualNone else mangaRepository.findAllByGenres(excludedGenres)

            val result = for {
                all <- eventualAll
                byTitle <- eventualByTitle
                byFranchise <- eventualByFranchise
                byIncludedGenres <- eventualByIncludedGenres
                byExcludedGenres <- eventualByExcludedGenres
            } yield (all.toSet, byTitle.toSet, byFranchise.toSet, byIncludedGenres.toSet, byExcludedGenres.toSet)

            result.map { case (all, byTitle, byFranchise, byIncludedGenres, byExcludedGenres) =>
                ((all & byTitle & byFranchise & byIncludedGenres) diff byExcludedGenres).toSeq
            }

        }
    }

}
