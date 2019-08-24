package mushtaq

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object ErrorHandling {

  val data: Map[String, Int] = Map("car" -> 100, "rocket" -> 800)

  def getPrice(productId: String): Future[Int] = {
    Timer.delay(100).map { _ =>
      data.getOrElse(productId, throw new RuntimeException("missing id"))
    }
  }

  def main(args: Array[String]): Unit = {
    val f1 = getPrice("car")
    f1.onComplete(x => println(x))
    val f2 = getPrice("maruti")
    f2.onComplete(x => println(x))

    Thread.sleep(1000)
    println(f1)
    println(f2)
  }

}
