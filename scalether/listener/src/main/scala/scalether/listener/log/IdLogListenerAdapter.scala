package scalether.listener.log

import cats.Id
import scalether.domain.request.LogFilter
import scalether.domain.response.Log

class IdLogListenerAdapter(listener: IdLogListener) extends LogListener[Id] {
  override def createFilter(fromBlock: String, toBlock: String): LogFilter =
    listener.createFilter(fromBlock, toBlock)

  override def onLog(log: Log, confirmations: Int, confirmed: Boolean): Unit =
    listener.onLog(log, confirmations, confirmed)

  override def enabled: Boolean = listener.isEnabled
}
