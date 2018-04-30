package scalether.transaction

import io.daonomic.blockchain.poller.mono.implicits._
import io.daonomic.cats.implicits._
import reactor.core.publisher.Mono
import scalether.core.MonoEthereum
import scalether.domain.Word
import scalether.domain.response.Transaction

class MonoTransactionReceivePoller(ethereum: MonoEthereum) extends TransactionReceivePoller[Mono](ethereum) {
  override def receiveTransaction(txHash: Mono[Word]): Mono[Transaction] = super.receiveTransaction(txHash)
}
