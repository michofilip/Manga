package controllers

import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import service.AccountService

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

@Singleton
class AccountController @Inject()(val controllerComponents: ControllerComponents,
                                  accountService: AccountService)
                                 (implicit ec: ExecutionContext) extends BaseController {

    def findById(accountId: Int): Action[AnyContent] = Action.async { implicit request =>
        accountService.findById(accountId).map {
            case Success(accountDetails) => Ok(Json.toJson(accountDetails))
            case Failure(e) => NotFound(e.getMessage)
        }
    }

}
