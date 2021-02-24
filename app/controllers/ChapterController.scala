package controllers

import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import service.ChapterService

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext

@Singleton
class ChapterController @Inject()(val controllerComponents: ControllerComponents,
                                  val chapterService: ChapterService)
                                 (implicit ec: ExecutionContext) extends BaseController {

    def findById(chapterId: Int): Action[AnyContent] = Action.async { implicit request =>
        chapterService.findById(chapterId).map {
            case Right(chapterDetails) => Ok(Json.toJson(chapterDetails))
            case Left(e) => NotFound(e.getMessage)
        }
    }

}
