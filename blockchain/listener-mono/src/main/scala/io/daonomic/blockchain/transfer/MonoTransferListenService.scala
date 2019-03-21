package io.daonomic.blockchain.transfer

import java.math.BigInteger

import io.daonomic.blockchain.MonoBlockchain
import io.daonomic.blockchain.poller.mono.implicits._
import io.daonomic.blockchain.state.{MonoState, MonoStateAdapter}
import io.daonomic.cats.mono.implicits._
import reactor.core.publisher.Mono

class MonoTransferListenService(blockchain: MonoBlockchain, confidence: Int, listener: MonoTransferListener, state: MonoState[BigInteger]) {
  private val scala = new TransferListenService[Mono](blockchain, confidence, new MonoTransferListenerAdapter(listener), new MonoStateAdapter[BigInteger](state))

  def check(block: BigInteger): Mono[Void] =
    scala.check(block).`then`()
}
