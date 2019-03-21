package scalether.test

import io.daonomic.rpc.mono.{WebSocketReconnectingClient, WebSocketRpcTransport}
import org.scalatest.FlatSpec
import org.scalatest.prop.PropertyChecks
import scalether.core.{Ethereum, MonoEthereum}

class WebSocketEthereumSpec extends FlatSpec with PropertyChecks {
  val ethereum = new MonoEthereum(new WebSocketRpcTransport(new WebSocketReconnectingClient("ops:8546"), Ethereum.mapper))

  "MonoEthereum" should "work with WebSocketRpcTransport" in {
    for (_ <- 1 to 100) {
      ethereum.ethBlockNumber().block()
    }
  }
}
