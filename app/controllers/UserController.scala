package controllers

import form.UserForm
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import service.UserService

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserController @Inject()(val controllerComponents: ControllerComponents,
                               val userService: UserService)
                              (implicit ec: ExecutionContext) extends BaseController {

    def findAll(): Action[AnyContent] = Action.async { implicit request =>
        userService.findAll().map { users =>
            Ok(Json.toJson(users))
        }
    }

    def findById(userId: Int): Action[AnyContent] = Action.async { implicit request =>
        userService.findById(userId).map {
            case Right(user) => Ok(Json.toJson(user))
            case Left(e) => NotFound(e.getMessage)
        }
    }

    def create(): Action[AnyContent] = Action.async { implicit request =>
        request.body.asJson
            .flatMap(json => Json.fromJson[UserForm](json).asOpt) match {
            case Some(userForm) =>
                userService.create(userForm).map { user =>
                    Ok(Json.toJson(user))
                }

            case None =>
                Future {
                    BadRequest
                }
        }
    }

    def update(): Action[AnyContent] = Action.async { implicit request =>
        request.body.asJson
            .flatMap(json => Json.fromJson[UserForm](json).asOpt) match {
            case Some(userForm) =>
                userService.update(userForm).map {
                    case Right(user) => Ok(Json.toJson(user))
                    case Left(e) => NotFound(e.getMessage)
                }

            case None =>
                Future {
                    BadRequest
                }
        }
    }

}
