package mushtaq

import java.util.concurrent.{ExecutorService, Executors}

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.stream.{Materializer, OverflowStrategy}
import akka.stream.scaladsl.Source

import scala.concurrent.{ExecutionContext, Future}

class BankAccount(implicit mat: Materializer) {
  private val rbiService = new RbiService

  val executorService: ExecutorService = Executors.newSingleThreadExecutor()
  implicit val ec: ExecutionContext    = ExecutionContext.fromExecutorService(executorService)

  private var _balance = 0

  val (queue, stream) = Source.queue[Action](1024, OverflowStrategy.dropHead).preMaterialize()

  def deposit(amount: Int): Future[Unit] = {
    rbiService.onNotify2(Deposit(amount)).map { _ =>
      _balance += amount
      queue.offer(Deposit(amount))
    }
  }

  def withdraw(amount: Int): Future[Unit] = {
    rbiService.onNotify2(Withdraw(amount)).map { _ =>
      _balance -= amount
      queue.offer(Withdraw(amount))
    }
  }

  def balance: Future[Int] = Future(_balance)
}
