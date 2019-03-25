package io.daonomic.blockchain.transaction

import java.math.BigInteger

import cats.Monad
import cats.implicits._
import io.daonomic.blockchain.Blockchain
import io.daonomic.blockchain.common.AbstractListenService
import io.daonomic.blockchain.poller.Notifier
import io.daonomic.blockchain.state.State
import io.daonomic.rpc.domain.Bytes

import scala.language.higherKinds

class TransactionListenService[F[_]](blockchain: Blockchain[F], confidence: Int, listener: TransactionListener[F], state: State[BigInteger, F])
                                    (implicit m: Monad[F], n: Notifier[F])
  extends AbstractListenService[F](confidence, state) {

  override protected def fetchAndNotify(latestBlock: BigInteger)(block: BigInteger): F[Unit] =
    blockchain.getTransactionIdsByBlock(block).flatMap(notifyListenerAboutTransactions(latestBlock, block))

  private def notifyListenerAboutTransactions(latestBlock: BigInteger, block: BigInteger)(transactionIds: List[Bytes]): F[Unit] = {
    n.notify(transactionIds)(notifyListener(latestBlock, block))
  }

  private def notifyListener(latestBlock: BigInteger, block: BigInteger)(transactionHash: Bytes): F[Unit] = {
    val confirmations = latestBlock.subtract(block).intValue() + 1
    listener.onTransaction(transactionHash, block, confirmations, confirmations >= confidence)
  }
}
