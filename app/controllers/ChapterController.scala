package controllers

import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import service.ChapterService

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

@Singleton
class ChapterController @Inject()(val controllerComponents: ControllerComponents,
                                  chapterService: ChapterService)
                                 (implicit ec: ExecutionContext) extends BaseController {

    def findById(chapterId: Int): Action[AnyContent] = Action.async { implicit request =>
        chapterService.findById(chapterId).map {
            case Success(chapterDetails) => Ok(Json.toJson(chapterDetails))
            case Failure(e) => NotFound(e.getMessage)
        }
    }

}
