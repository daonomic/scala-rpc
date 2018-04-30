package scalether.transaction

import java.math.BigInteger

import cats.implicits._
import cats.MonadError
import scalether.core.Ethereum
import scalether.domain.{Address, Binary}
import scalether.domain.request.Transaction

import scala.language.higherKinds

abstract class AbstractTransactionSender[F[_]](val ethereum: Ethereum[F], val from: Address, val gas: BigInteger, val gasPriceProvider: GasPriceProvider[F])
                                              (implicit me: MonadError[F, Throwable])
  extends TransactionSender[F] {

  def call(transaction: Transaction): F[Binary] =
    ethereum.ethCall(transaction.copy(from = from), "latest")

  override def estimate(transaction: Transaction): F[BigInteger] =
    ethereum.ethEstimateGas(transaction.copy(from = from), "latest")

  protected def fill(transaction: Transaction): F[Transaction] =
    for {
      gasPrice <- gasPriceProvider.gasPrice
      gas <- getGas(transaction)
    } yield
      transaction.copy(
        from = from,
        gas = gas,
        gasPrice = gasPrice
      )

  private def getGas(transaction: Transaction): F[BigInteger] = {
    if (transaction.gas == null) {
      me.pure(gas)
    } else if (gas.compareTo(transaction.gas) >= 0) {
      me.pure(transaction.gas)
    } else {
      me.raiseError(new IllegalArgumentException(s"request more gas(${transaction.gas}) than max gas in transaction sender($gas)"))
    }
  }
}
