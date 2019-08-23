package mushtaq

import java.util.concurrent.{ExecutorService, Executors}
import java.util.function.Consumer

import scala.concurrent.{ExecutionContext, ExecutionContextExecutorService, Future, Promise}

class ProductService2(productService: ProductService) {

  val executorService: ExecutorService      = Executors.newSingleThreadExecutor()
  private implicit val ec: ExecutionContext = ExecutionContext.fromExecutorService(executorService)

  val data: Map[String, Int] = Map("car" -> 100, "rocket" -> 800)

  def getPrice(productId: String): Future[Int] = {
    val promise: Promise[Int] = Promise()
    productService.onPrice(productId, x => promise.success(x))
    promise.future
  }

  def totalPriceSequential(id1: String, id2: String): Future[Int] = {
    getPrice(id1).flatMap { price1 =>
      getPrice(id2).map { price2 =>
        price1 + price2
      }
    }
  }

  def totalPricePar(id1: String, id2: String): Future[Int] = {
    val f1 = getPrice(id1)
    val f2 = getPrice(id2)
    f1.flatMap { price1 =>
      f2.map { price2 =>
        price1 + price2
      }
    }
  }

}
