package mushtaq

import java.util.concurrent.{Executors, TimeUnit}
import java.util.function.Consumer

class ProductService {
  val executorService = Executors.newScheduledThreadPool(1)

  val data: Map[String, Int] = Map("car" -> 100, "rocket" -> 800)

  def onPrice(productId: String, callback: Consumer[Int]): Unit = {
    val op: Runnable = () => callback.accept(data(productId))
    executorService.schedule(op, 1, TimeUnit.SECONDS)
  }

  def totalPriceSequential(id1: String, id2: String, callback: Consumer[Int]) = {
    onPrice(id1, price1 => {
      onPrice(id2, price2 => {
        callback.accept(price1 + price2)
      })
    })
  }

  def totalPricePar(id1: String, id2: String, callback: Consumer[Int]) = {
    var total = 0
    onPrice(id1, price => {
      if (total == 0) {
        total += price
      } else {
        callback.accept(total + price)
      }
    })
    onPrice(id2, price => {
      if (total == 0) {
        total += price
      } else {
        callback.accept(total + price)
      }
    })
  }

  onPrice("car", price => println(price))
  totalPriceSequential("car", "rocket", sum => println(sum))

}
