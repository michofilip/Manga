package controllers

import form.TagForm
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import service.{MangaTagService, TagService}

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

@Singleton
class TagController @Inject()(val controllerComponents: ControllerComponents,
                              tagService: TagService,
                              mangaTagService: MangaTagService)
                             (implicit ec: ExecutionContext) extends BaseController {

    def create(): Action[AnyContent] = Action.async { implicit request =>
        request.body.asJson.flatMap { json =>
            Json.fromJson[TagForm](json).asOpt
        } match {
            case Some(tagForm) => tagService.create(tagForm).map {
                case Success(tag) => Ok(Json.toJson(tag))
                case Failure(exception) => UnprocessableEntity(exception.getMessage)
            }

            case None => Future {
                BadRequest
            }
        }
    }

    def update(): Action[AnyContent] = Action.async { implicit request =>
        request.body.asJson.flatMap { json =>
            Json.fromJson[TagForm](json).asOpt
        } match {
            case Some(tagForm) => tagService.update(tagForm).map {
                case Success(tag) => Ok(Json.toJson(tag))
                case Failure(exception) => NotFound(exception.getMessage)
            }

            case None => Future {
                BadRequest
            }
        }
    }

    def delete(tagId: Int): Action[AnyContent] = Action.async { implicit request =>
        tagService.delete(tagId).map {
            case Success(_) => NoContent
            case Failure(exception) => NotFound(exception.getMessage)
        }
    }

    def assignTag(tagId: Int, mangaId: Int): Action[AnyContent] = Action.async { implicit request =>
        mangaTagService.assignTag(tagId, mangaId).map {
            case Success(_) => NoContent
            case Failure(exception) => UnprocessableEntity(exception.getMessage)
        }
    }

    def unAssignTag(tagId: Int, mangaId: Int): Action[AnyContent] = Action.async { implicit request =>
        mangaTagService.unAssignTag(tagId, mangaId).map {
            case Success(_) => NoContent
            case Failure(exception) => NotFound(exception.getMessage)
        }
    }

}
