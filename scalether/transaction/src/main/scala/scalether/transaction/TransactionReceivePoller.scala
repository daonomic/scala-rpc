package scalether.transaction

import cats.Monad
import cats.implicits._
import io.daonomic.blockchain.poller.Poller
import scalether.core.Ethereum
import scalether.domain.Word
import scalether.domain.response.Transaction

import scala.language.higherKinds

class TransactionReceivePoller[F[_]](val ethereum: Ethereum[F])
                                    (implicit f: Monad[F], poller: Poller[F]) {

  def receiveTransaction(txHash: F[Word]): F[Transaction] = for {
    hash <- txHash
    result <- poller.poll(1000)(pollForTransaction(hash))
  } yield result

  private def pollForTransaction(txHash: Word): F[Option[Transaction]] =
    ethereum.ethGetTransactionByHash(txHash)
}
