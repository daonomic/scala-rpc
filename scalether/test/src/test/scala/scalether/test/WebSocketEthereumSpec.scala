package scalether.test

import io.daonomic.rpc.domain.Word
import io.daonomic.rpc.mono.{WebSocketReconnectingClient, WebSocketRpcTransport}
import org.scalatest.FlatSpec
import org.scalatest.prop.PropertyChecks
import scalether.core.{Ethereum, MonoEthereum}

class WebSocketEthereumSpec extends FlatSpec with PropertyChecks {
  val ethereum = new MonoEthereum(new WebSocketRpcTransport(new WebSocketReconnectingClient("localhost:8546"), Ethereum.mapper))

  "MonoEthereum" should "work with WebSocketRpcTransport" in {
    ethereum.ethBlockNumber().block()
  }

  it should "return empty Mono if tx not found" in {
    val result = ethereum.ethGetTransactionByHash(Word.apply("0xad962f134127cee64c034d782d05c57eeeda5e20a8b0f078761278cdf7527829")).block()
    assert(result.isEmpty)
  }
}
