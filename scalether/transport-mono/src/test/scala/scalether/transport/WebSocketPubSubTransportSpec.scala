package scalether.transport

import io.daonomic.rpc.ManualTag
import org.scalatest.FlatSpec
import scalether.core.EthPubSub
import scalether.domain.request.LogFilter

class WebSocketPubSubTransportSpec extends FlatSpec {
  val pubSub = new EthPubSub(new WebSocketPubSubTransport("ether-ropsten:8546"))

  "WebSocketPubSubTransport" should "listen to newHeads" taggedAs ManualTag in {

    pubSub.newHeads()
      .doOnNext(m => println(s"newHead: $m"))
      .take(1)
      .blockLast()

    println("got results. waiting")

    Thread.sleep(100000)
  }

  it should "listen to new logs" taggedAs ManualTag in {
    pubSub.logs(LogFilter())
      .doOnNext(m => println(s"newLog: $m"))
      .take(10000)
      .blockLast()
  }
}
