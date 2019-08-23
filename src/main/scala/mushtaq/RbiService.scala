package mushtaq

import java.util.concurrent.{Executors, TimeUnit}

class RbiService {
  val executorService = Executors.newScheduledThreadPool(1)

  def notify(action: Action): Unit = {
    Thread.sleep(1000)
    ()
  }

  def onNotify(action: Action)(onSuccess: Runnable): Unit = {
    executorService.schedule(onSuccess, 1, TimeUnit.SECONDS)
  }

}
