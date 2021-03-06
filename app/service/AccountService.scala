package service

import db.mangas.repository.AccountRepository
import dto.{AccountDetails, Account}
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

            case Some(accountEntity) =>
                userService.findById(accountEntity.userId).flatMap {
                    case Success(user) =>
                        val data = for {
                            accountMangas <- accountMangaService.findAllByAccount(accountId)
                            tags <- tagService.findAllByAccountId(accountId)
                        } yield (accountMangas, tags)

                        data.map { case (accountMangas, tags) =>
                            val account = Account(
                                id = accountEntity.id,
                                isActive = accountEntity.isActive,
                                user = user
                            )

                            Success {
                                AccountDetails(
                                    account = account,
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
