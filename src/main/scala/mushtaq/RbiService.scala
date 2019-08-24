package mushtaq

import java.util.concurrent.{Executors, TimeUnit}

import scala.concurrent.Future

class RbiService {
  val executorService = Executors.newScheduledThreadPool(1)

  def notify(action: Action): Unit = {
    Thread.sleep(1000)
    ()
  }

  def onNotify(action: Action)(onSuccess: Runnable): Unit = {
    executorService.schedule(onSuccess, 1, TimeUnit.SECONDS)
  }

  def onNotify2(action: Action): Future[Unit] = {
    Timer.delay(1000)
  }

}
