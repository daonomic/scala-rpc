package scalether.transaction

import io.daonomic.blockchain.poller.mono.implicits._
import io.daonomic.cats.mono.implicits._
import io.daonomic.rpc.domain.Word
import reactor.core.publisher.Mono
import scalether.core.MonoEthereum
import scalether.domain.response.Transaction

class MonoTransactionReceivePoller(ethereum: MonoEthereum) extends TransactionReceivePoller[Mono](ethereum) {
  override def receiveTransaction(txHash: Word): Mono[Transaction] = super.receiveTransaction(txHash)
}
