package scalether.transaction

import java.math.BigInteger

import cats.implicits._
import io.daonomic.cats.MonadThrowable
import io.daonomic.rpc.domain.Binary
import org.slf4j.{Logger, LoggerFactory}
import scalether.core.Ethereum
import scalether.domain.request.Transaction
import scalether.domain.Address

import scala.language.higherKinds

abstract class AbstractTransactionSender[F[_]](val ethereum: Ethereum[F], val from: Address, val gas: BigInteger, val gasPriceProvider: GasPriceProvider[F])
                                              (implicit me: MonadThrowable[F])
  extends TransactionSender[F] {

  protected val logger: Logger = LoggerFactory.getLogger(getClass)
  logger.info(s"created from=$from")

  def call(transaction: Transaction): F[Binary] = {
    val tx = transaction.copy(from = Option(transaction.from).getOrElse(from))
    ethereum.ethCall(tx, "latest")
  }

  override def estimate(transaction: Transaction): F[BigInteger] =
    ethereum.ethEstimateGas(transaction.copy(from = Option(transaction.from).getOrElse(from)), "latest")

  protected def fill(transaction: Transaction): F[Transaction] =
    for {
      gasPrice <- gasPriceProvider.gasPrice
      gas <- getGas(transaction)
    } yield
      transaction.copy(
        from = Option(transaction.from).getOrElse(from),
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
