package scalether.transaction

import java.math.BigInteger

import reactor.core.publisher.Mono
import scalether.domain.request.Transaction
import scalether.domain.{Binary, Word}

trait MonoTransactionSender extends TransactionSender[Mono] {
  def call(transaction: Transaction): Mono[Binary]
  def estimate(transaction: Transaction): Mono[BigInteger]
  def sendTransaction(transaction: Transaction): Mono[Word]
}
