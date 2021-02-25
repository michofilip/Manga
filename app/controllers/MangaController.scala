package controllers

import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import service.MangaService

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext

@Singleton
class MangaController @Inject()(val controllerComponents: ControllerComponents,
                                val mangaService: MangaService)
                               (implicit ec: ExecutionContext) extends BaseController {

    def findAll(): Action[AnyContent] = Action.async { implicit request =>
        mangaService.findAll().map { mangas =>
            Ok(Json.toJson(mangas))
        }
    }

    def findById(mangaId: Int): Action[AnyContent] = Action.async { implicit request =>
        mangaService.findById(mangaId).map {
            case Right(mangaDetails) => Ok(Json.toJson(mangaDetails))
            case Left(e) => NotFound(e.getMessage)
        }
    }

    def findAllBySearchParameters(maybeTitle: Option[String],
                                  maybeFranchise: Option[String],
                                  includedGenres: Seq[String],
                                  excludedGenres: Seq[String]): Action[AnyContent] = Action.async { implicit request =>
        mangaService.findAllBySearchParameters(maybeTitle, maybeFranchise, includedGenres, excludedGenres).map { mangas =>
            Ok(Json.toJson(mangas))
        }
    }

}
