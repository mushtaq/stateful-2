package mushtaq

import java.util.concurrent.{ExecutorService, Executors}

object BankAccountTest {
  val account = new BankAccount

  def main(args: Array[String]): Unit = {
    val executorService = Executors.newScheduledThreadPool(1000)

    val runnable: Runnable = { () =>
      account.deposit(100)
    }

    val runnable2: Runnable = { () =>
      account.withdraw(100)
    }

    (1 to 10000).foreach { _ =>
      executorService.submit(runnable)
    }

    (1 to 10000).foreach { _ =>
      executorService.submit(runnable2)
    }

    Thread.sleep(2000)

    println(account.balance)
  }
}
