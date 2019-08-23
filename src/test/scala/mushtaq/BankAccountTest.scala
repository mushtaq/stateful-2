package mushtaq

import org.scalatest.FunSuite

class BankAccountTest extends FunSuite {

  test("asdasd") {
    val account = new BankAccount

    account.deposit(100)
    account.withdraw(100)
    println(account.balance)
  }

}
