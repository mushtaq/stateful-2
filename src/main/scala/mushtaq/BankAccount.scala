package mushtaq

class BankAccount {

  private var _balance = 0

  def deposit(amount: Int): Unit = synchronized {
    _balance += amount
  }

  def withdraw(amount: Int): Unit = synchronized {
    _balance -= amount
  }

  def balance: Int = synchronized {
    _balance
  }
}
