package mushtaq

import java.util.concurrent.Executors

class BankAccount {
  private val rbiService = new RbiService

  private var _balance = 0

  def deposit(amount: Int): Unit = {
    rbiService.onNotify(Deposit(amount)) { () =>
      synchronized {
        _balance += amount
      }
    }

  }

  def withdraw(amount: Int): Unit = {
    rbiService.onNotify(Withdraw(amount)) { () =>
      synchronized {
        _balance -= amount
      }
    }

  }

  def balance: Int = synchronized {
    _balance
  }
}
