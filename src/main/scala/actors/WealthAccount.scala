package actors

import java.util.concurrent.{ExecutorService, Executors}

import akka.NotUsed
import akka.stream.Materializer
import akka.stream.scaladsl.Source
import mushtaq.{Action, BankAccount, Deposit, Withdraw}

import scala.concurrent.{ExecutionContext, Future}

class WealthAccount(bankAccount: List[BankAccount])(implicit mat: Materializer) {

  val executorService: ExecutorService = Executors.newSingleThreadExecutor()
  implicit val ec: ExecutionContext    = ExecutionContext.fromExecutorService(executorService)

  var _balance = 0

  val combinedActions: Source[Action, NotUsed] = bankAccount
    .map(_.stream)
    .foldLeft(Source.empty[Action])(_ merge _)

  val balanceStream: Source[Int, NotUsed] = combinedActions.scan(0) { (accBalance, action) =>
    action match {
      case Deposit(amount)  => accBalance + amount
      case Withdraw(amount) => accBalance - amount
    }
  }

  balanceStream.mapAsync(1)(b => Future(_balance = b))

  def balance: Future[Int] = Future(_balance)
}
