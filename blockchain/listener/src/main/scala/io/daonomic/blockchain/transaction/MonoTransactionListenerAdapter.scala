package io.daonomic.blockchain.transaction

import java.math.BigInteger

import reactor.core.publisher.Mono

class MonoTransactionListenerAdapter(listener: MonoTransactionListener) extends TransactionListener[Mono] {
  override def onTransaction(transactionHash: String, blockNumber: BigInteger, confirmations: Int, confirmed: Boolean): Mono[Unit] =
    listener.onTransaction(transactionHash, blockNumber, confirmations, confirmed)
      .`then`(Mono.just())
}
