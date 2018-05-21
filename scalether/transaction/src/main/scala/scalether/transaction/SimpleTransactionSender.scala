package scalether.transaction

import java.math.BigInteger

import cats.implicits._
import io.daonomic.cats.MonadThrowable
import io.daonomic.rpc.domain.Word
import scalether.core.Ethereum
import scalether.domain.request.Transaction
import scalether.domain.Address

import scala.language.higherKinds

class SimpleTransactionSender[F[_]](ethereum: Ethereum[F], from: Address, gas: BigInteger, gasPrice: GasPriceProvider[F])
                                   (implicit m: MonadThrowable[F])
  extends AbstractTransactionSender[F](ethereum, from, gas, gasPrice) {

  def sendTransaction(transaction: Transaction): F[Word] = fill(transaction).flatMap {
    t => ethereum.ethSendTransaction(t)
  }
}
