package controllers

import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import service.GenreService

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext

@Singleton
class GenreController @Inject()(val controllerComponents: ControllerComponents,
                                genreService: GenreService)
                               (implicit ec: ExecutionContext) extends BaseController {

    def findAll(): Action[AnyContent] = Action.async { implicit request =>
        genreService.findAll().map { genres =>
            Ok(Json.toJson(genres))
        }
    }

}
