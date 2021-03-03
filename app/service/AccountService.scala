package service

import db.mangas.repository.AccountRepository
import dto.AccountDetails
import utils.ExceptionUtils

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

@Singleton
class AccountService @Inject()(accountRepository: AccountRepository,
                               accountMangaService: AccountMangaService,
                               userService: UserService)
                              (implicit ec: ExecutionContext) {

    def findById(accountId: Int): Future[Try[AccountDetails]] = {
        accountRepository.findById(accountId).flatMap {
            case None =>
                ExceptionUtils.noSuchElementException(s"Account id $accountId not found!")

            case Some(account) =>
                userService.findById(account.userId).flatMap {
                    case Success(user) =>
                        accountMangaService.findAllByAccount(accountId).map { mangas =>
                            Success {
                                AccountDetails(
                                    account = account,
                                    user = user,
                                    mangas = mangas
                                )
                            }
                        }

                    case Failure(exception) =>
                        ExceptionUtils.futureFailure(exception)
                }
        }
    }

}
