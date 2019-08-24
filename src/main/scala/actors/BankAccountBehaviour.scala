package actors

import akka.actor.Scheduler
import akka.actor.typed.{ActorRef, ActorSystem, Behavior}
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.scaladsl.AskPattern._
import akka.util.Timeout

import scala.concurrent.Future
import scala.concurrent.duration.DurationDouble

object BankAccountBehaviour {

  case class AccountState(id: String, balance: Int, actions: List[Action]) {
    def addAmount(amount: Int): AccountState        = copy(balance = balance + amount)
    def removeAmount(amount: Int): AccountState     = copy(balance = balance - amount)
    def updateActions(action: Action): AccountState = copy(actions = action :: actions)
  }

  sealed trait Action
  case class Deposit(amount: Int)                          extends Action
  case class Withdraw(amount: Int)                         extends Action
  case class GetBalance(requester: ActorRef[AccountState]) extends Action

  def behavior(accountState: AccountState): Behavior[Action] = Behaviors.receiveMessage {
    case Deposit(amount) =>
      behavior(accountState.addAmount(amount).updateActions(Deposit(amount)))
    case Withdraw(amount) =>
      behavior(accountState.removeAmount(amount).updateActions(Deposit(amount)))
    case GetBalance(requester) =>
      requester ! accountState
      Behaviors.same
  }

  def main(args: Array[String]): Unit = {
    val actorSystem = ActorSystem(behavior(AccountState("1", 0, Nil)), "demo")
    import actorSystem.executionContext
    implicit val timeout: Timeout     = Timeout(1.second)
    implicit val scheduler: Scheduler = actorSystem.scheduler

    actorSystem ! Deposit(100)
    actorSystem ! Deposit(200)
    val result: Future[AccountState] = actorSystem.ask { requester: ActorRef[AccountState] =>
      GetBalance(requester)
    }

    result.onComplete(println)
  }

}
