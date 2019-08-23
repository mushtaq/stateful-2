package mushtaq

sealed trait Action
case class Deposit(amount: Int)  extends Action
case class Withdraw(amount: Int) extends Action
