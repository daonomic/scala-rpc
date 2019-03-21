package scalether.core

import io.daonomic.rpc.domain.Word
import reactor.core.publisher.Flux
import scalether.domain.request.LogFilter
import scalether.domain.response.{Block, Log}

class EthPubSub(transport: PubSubTransport) {

  def newHeads(): Flux[Block[Word]] =
    transport.subscribe("newHeads")

  def logs(logFilter: LogFilter): Flux[Log] =
    transport.subscribe("logs", Some(logFilter))
}

trait PubSubTransport {
  def subscribe[T : Manifest](name: String, param: Option[Any] = None): Flux[T]
}