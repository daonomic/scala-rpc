package scalether.listener.log

import scalether.domain.request.LogFilter
import scalether.domain.response.Log

import scala.language.higherKinds

trait LogListener[F[_]] {
  def enabled: Boolean
  def createFilter(fromBlock: String, toBlock: String): F[LogFilter]
  def onLog(log: Log, confirmations: Int, confirmed: Boolean): F[Unit]
}
