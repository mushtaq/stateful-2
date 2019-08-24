package mushtaq

import java.util.concurrent.{Executors, ScheduledExecutorService, TimeUnit}

import scala.concurrent.{ExecutionContext, ExecutionContextExecutorService, Future, Promise}

object Timer {

  private val service: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
  implicit val ec: ExecutionContext             = ExecutionContext.fromExecutorService(service)
  Thread.sleep(1000)

  def sleep(millis: Long): Unit = ???

  def delay(millis: Long): Future[Unit] = {
    val p: Promise[Unit] = Promise()
    val op: Runnable     = () => p.success(())
    service.schedule(op, millis, TimeUnit.MILLISECONDS)
    p.future
  }

  def squareAsync(x: Int): Future[Int] = delay(1000).map(_ => x * x)
  def squareAllAsync(xs: List[Int]): Future[List[Int]] = {
    val futures: List[Future[Int]] = xs.map(x => squareAsync(x))
    Future.sequence(futures)
  }

  def main(args: Array[String]): Unit = {
    val begin = System.currentTimeMillis()
    delay(10).onComplete { _ =>
      println(System.currentTimeMillis() - begin)
    }

    squareAllAsync((1 to 100).toList).onComplete(x => println(x))

  }
}
