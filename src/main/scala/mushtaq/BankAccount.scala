package mushtaq

class BankAccount {

  private val rbiService = new RbiService

  private var _balance = 0

  def deposit(amount: Int): Unit = synchronized {
     rbiService.notify(Deposit(amount))
    _balance += amount
  }

  def withdraw(amount: Int): Unit = synchronized {
    rbiService.notify(Withdraw(amount))
    _balance -= amount
  }

  def balance: Int = synchronized {
    _balance
  }
}
