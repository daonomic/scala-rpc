package io.daonomic.blockchain.transaction

import java.math.BigInteger

import io.daonomic.blockchain.MonoBlockchain
import io.daonomic.blockchain.poller.mono.implicits._
import io.daonomic.blockchain.state.{MonoState, MonoStateAdapter}
import io.daonomic.cats.mono.implicits._
import reactor.core.publisher.Mono

class MonoTransactionListenService(blockchain: MonoBlockchain, confidence: Int, listener: MonoTransactionListener, state: MonoState[BigInteger]) {
  private val scala = new TransactionListenService[Mono](blockchain, confidence, new MonoTransactionListenerAdapter(listener), new MonoStateAdapter[BigInteger](state))

  def check(block: BigInteger): Mono[Void] =
    scala.check(block).`then`()
}
