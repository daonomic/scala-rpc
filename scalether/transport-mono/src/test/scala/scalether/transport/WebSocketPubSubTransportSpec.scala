package scalether.transport

import java.math.BigInteger

import io.daonomic.cats.mono.implicits._
import io.daonomic.rpc.mono.{WebSocketReconnectingClient, WebSocketRpcTransport}
import org.scalatest.FlatSpec
import org.web3j.utils
import reactor.core.publisher.Mono
import scalether.core.{EthPubSub, Ethereum, MonoEthereum}
import scalether.domain.request.Transaction
import scalether.sync.SemaphoreMonoSynchronizer
import scalether.transaction.{SigningTransactionSender, SimpleNonceProvider, ValGasPriceProvider}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

class WebSocketPubSubTransportSpec extends FlatSpec {
  val ethereum = new MonoEthereum(new WebSocketRpcTransport(new WebSocketReconnectingClient("localhost:8546"), Ethereum.mapper))
  val sender = new SigningTransactionSender[Mono](
    ethereum,
    new SimpleNonceProvider[Mono](ethereum),
    new SemaphoreMonoSynchronizer(),
    utils.Numeric.toBigInt("0x00120de4b1518cf1f16dc1b02f6b4a8ac29e870174cb1d8575f578480930250a"),
    BigInteger.valueOf(2000000),
    new ValGasPriceProvider[Mono](BigInteger.ZERO)
  )
  val pubSub = new EthPubSub(new WebSocketPubSubTransport("localhost:8546"))

  "WebSocketPubSubTransport" should "listen to newHeads" in {

    val future = Future {
      pubSub.newHeads()
        .take(1)
        .blockLast()
    }

    val hash = sender.sendTransaction(Transaction(to = sender.from, value = BigInteger.ONE)).block()
    val receipt = ethereum.ethGetTransactionReceipt(hash).block().get

    val result = Await.result(future, Duration.Inf)
    assert(receipt.blockHash == result.hash)
  }
}
