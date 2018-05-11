package scalether.transaction

import java.math.BigInteger

import scalether.core.Ethereum
import scalether.domain.request.Transaction
import scalether.domain.{Binary, Word}

import scala.language.higherKinds

trait TransactionSender[F[_]] {
  val ethereum: Ethereum[F]
  def call(transaction: Transaction): F[Binary]
  def estimate(transaction: Transaction): F[BigInteger]
  def sendTransaction(transaction: Transaction): F[Word]
}
