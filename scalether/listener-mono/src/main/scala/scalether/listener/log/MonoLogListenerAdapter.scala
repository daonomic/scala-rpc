package scalether.listener.log

import reactor.core.publisher.Mono
import scalether.domain.request.LogFilter
import scalether.domain.response.Log

class MonoLogListenerAdapter(listener: MonoLogListener) extends LogListener[Mono] {
  override def createFilter(fromBlock: String, toBlock: String): Mono[LogFilter] =
    listener.createFilter(fromBlock, toBlock)

  override def onLog(log: Log, confirmations: Int, confirmed: Boolean): Mono[Unit] =
    listener.onLog(log, confirmations, confirmed)
      .`then`(Mono.just())

  override def enabled: Boolean = listener.isEnabled
}
