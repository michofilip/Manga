package service

import db.mangas.repository.AccountRepository
import dto.{Account, AccountDetails}
import utils.ExceptionUtils

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

@Singleton
class AccountService @Inject()(accountRepository: AccountRepository,
                               accountMangaService: AccountMangaService,
                               tagService: TagService,
                               userService: UserService)
                              (implicit ec: ExecutionContext) {

    def findById(accountId: Int): Future[Try[AccountDetails]] = {
        accountRepository.findById(accountId).flatMap {
            case None =>
                ExceptionUtils.noSuchElementException(s"Account id $accountId not found!")

            case Some(account) =>
                userService.findById(account.userId).flatMap {
                    case Success(user) =>
                        val data = for {
                            accountMangas <- accountMangaService.findAllByAccount(accountId)
                            tags <- tagService.findAllByAccountId(accountId)
                        } yield (accountMangas, tags)

                        data.map { case (accountMangas, tags) =>
                            Success {
                                AccountDetails(
                                    account = Account.fromEntity(account),
                                    user = user,
                                    tags = tags,
                                    accountMangas = accountMangas
                                )
                            }
                        }

                    case Failure(exception) =>
                        ExceptionUtils.futureFailure(exception)
                }
        }
    }

}
