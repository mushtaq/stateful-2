package mushtaq

import java.util.concurrent.{Callable, Executors}
import java.util.function.Consumer

class BankAccount {
  private val rbiService = new RbiService
  val executorService    = Executors.newSingleThreadExecutor()

  private var _balance = 0

  def deposit(amount: Int): Unit = {
    rbiService.onNotify(Deposit(amount)) { () =>
      val op: Runnable = () => _balance += amount
      executorService.submit(op)
    }

  }

  def withdraw(amount: Int): Unit = {
    rbiService.onNotify(Withdraw(amount)) { () =>
      val op: Runnable = () => _balance -= amount
      executorService.submit(op)
    }

  }

  def onBalance(callback: Consumer[Int]): Unit = {
    val op: Runnable = () => callback.accept(_balance)
    executorService.submit(op)
  }
}
