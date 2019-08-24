package mushtaq

import java.util.concurrent.{ExecutorService, Executors}

import scala.concurrent.{ExecutionContext, Future}

class BankAccount {
  private val rbiService               = new RbiService
  val executorService: ExecutorService = Executors.newSingleThreadExecutor()
  implicit val ec: ExecutionContext    = ExecutionContext.fromExecutorService(executorService)

  private var _balance = 0

  def deposit(amount: Int): Future[Unit] = {
    rbiService.onNotify2(Deposit(amount)).map { _ =>
      _balance += amount
    }
  }

  def withdraw(amount: Int): Future[Unit] = {
    rbiService.onNotify2(Withdraw(amount)).map { _ =>
      _balance -= amount
    }
  }

  def balance: Future[Int] = Future(_balance)
}
