package mushtaq

import java.util.concurrent.Executors

import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration.DurationDouble

object NonBlocking {
  import scala.concurrent.ExecutionContext.Implicits.global
  def incrementAsync(x: Int): Future[Int] = {
    Timer.delay(1000).map(_ => x + 1)
  }

}

object Blocking {
  implicit val ec = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(4000))
  def jdbcSquareAsync(x: Int): Future[Int] = {
    Future {
      Thread.sleep(1000)
      x * x
    }
  }
}

object Test1 extends App {
  import scala.concurrent.ExecutionContext.Implicits.global
  val futures = (1 to 100).map(x => NonBlocking.incrementAsync(x))
  val dd = concurrent.blocking {
    Thread.sleep(100)
    100
  }
  val result  = Await.result(Future.sequence(futures), 1000.seconds)
  println(result)
}

object Test2 extends App {
  import scala.concurrent.ExecutionContext.Implicits.global
  val futures = (1 to 100).map(x => Blocking.jdbcSquareAsync(x))
  val result  = Await.result(Future.sequence(futures), 10000000.seconds)
  println(result)
}

object Test3 extends App {
  import scala.concurrent.ExecutionContext.Implicits.global

  val futures  = (1 to 1000).map(x => Blocking.jdbcSquareAsync(x))
  val futures2 = (1 to 1000).map(x => NonBlocking.incrementAsync(x))

  futures.foreach(_.onComplete(x => println(s"jdbc    : $x")))
  futures2.foreach(_.onComplete(x => println(s"increment: $x")))

  Await.result(Future.sequence(futures), 10000000.seconds)
  Await.result(Future.sequence(futures2), 10000000.seconds)
}
