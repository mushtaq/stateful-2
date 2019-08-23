package mushtaq

import java.util.concurrent.Executors

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

  def balance: Int =  {
    _balance
  }
}
