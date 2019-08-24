package mushtaq

import java.util.concurrent.{ExecutorService, Executors}

import scala.concurrent.ExecutionContext

object BankAccountTest {
  val account = new BankAccount

  def main(args: Array[String]): Unit = {
    val executorService               = Executors.newScheduledThreadPool(1000)
    implicit val ec: ExecutionContext = ExecutionContext.fromExecutorService(executorService)

    val runnable: Runnable = { () =>
      account.deposit(100)
    }

    val runnable2: Runnable = { () =>
      account.withdraw(100)
    }

    (1 to 50000).foreach { _ =>
      executorService.submit(runnable)
    }

    (1 to 50000).foreach { _ =>
      executorService.submit(runnable2)
    }

    Thread.sleep(4000)

    account.balance.foreach { x =>
      println(x)
    }
  }
}
