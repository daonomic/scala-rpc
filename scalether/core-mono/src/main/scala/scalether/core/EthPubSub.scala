package scalether.core

import io.daonomic.rpc.domain.Word
import reactor.core.publisher.Flux
import scalether.domain.response.Block

class EthPubSub(transport: PubSubTransport) {
  def newHeads(): Flux[Block[Word]] =
    transport.subscribe("newHeads")
}

trait PubSubTransport {
  def subscribe[T : Manifest](name: String, param: Option[Any] = None): Flux[T]
}