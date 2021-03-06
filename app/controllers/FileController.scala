package controllers

import akka.NotUsed
import akka.stream.scaladsl.Source
import akka.util.ByteString
import play.api.http.HttpEntity
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import service.FileService

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

@Singleton
class FileController @Inject()(val controllerComponents: ControllerComponents,
                               fileService: FileService)
                              (implicit ec: ExecutionContext) extends BaseController {

    def findByKey(key: String): Action[AnyContent] = Action.async { implicit request =>
        fileService.findByKey(key).map {
            case Success(file) =>
                val byteString: ByteString = ByteString(file.content)
                val source: Source[ByteString, NotUsed] = Source.single(byteString)
                val contentLength = Some(file.contentLength)
                val contentType = Some(file.contentType)
                val fileName = Option(file.fileName)

                Ok.sendEntity(
                    entity = HttpEntity.Streamed(source, contentLength, contentType),
                    inline = true,
                    fileName = fileName
                )

            case Failure(e) =>
                NotFound(e.getMessage)
        }
    }

}
