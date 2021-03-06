package controllers

import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import service.FranchiseService

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext

@Singleton
class FranchiseController @Inject()(val controllerComponents: ControllerComponents,
                                    franchiseService: FranchiseService)
                                   (implicit ec: ExecutionContext) extends BaseController {

    def findAll(): Action[AnyContent] = Action.async { implicit request =>
        franchiseService.findAll().map { franchises =>
            Ok(Json.toJson(franchises))
        }
    }

}
