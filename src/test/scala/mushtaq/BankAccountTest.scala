package mushtaq

import java.util.concurrent.{ExecutorService, Executors}

import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration.DurationDouble

object BankAccountTest {
  val account = new BankAccount

  val executorService = Executors.newScheduledThreadPool(1000)
  def main(args: Array[String]): Unit = {
    implicit val ec: ExecutionContext = ExecutionContext.fromExecutorService(executorService)

    val depositFutures = (1 to 50000).map { _ =>
      account.deposit(100)
    }

    val withdrawFutures = (1 to 50000).map { _ =>
      account.withdraw(100)
    }

    val resultF = Future
      .sequence(depositFutures ++ withdrawFutures)
      .flatMap { _ =>
        account.balance.map { x =>
          println(x)
        }
      }

    Await.result(resultF, 1000.seconds)

    executorService.shutdown()

  }
}
