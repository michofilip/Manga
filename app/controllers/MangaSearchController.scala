package controllers

import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import service.MangaSearchService

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext

@Singleton
class MangaSearchController @Inject()(val controllerComponents: ControllerComponents,
                                      val mangaSearchService: MangaSearchService)
                                     (implicit ec: ExecutionContext) extends BaseController {

    def findAllBySearchParameters(maybeTitle: Option[String],
                                  maybeFranchise: Option[String],
                                  includedGenres: Seq[String],
                                  excludedGenres: Seq[String]): Action[AnyContent] = Action.async { implicit request =>
        mangaSearchService.findAllBySearchParameters(maybeTitle, maybeFranchise, includedGenres, excludedGenres).map { mangas =>
            Ok(Json.toJson(mangas))
        }
    }

}
