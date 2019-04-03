package scalether.transaction

import java.math.BigInteger

import io.daonomic.rpc.domain.{Binary, Word}
import reactor.core.publisher.Mono
import scalether.core.MonoEthereum
import scalether.domain.request.Transaction

trait MonoTransactionSender extends TransactionSender[Mono] {
  override val ethereum: MonoEthereum
  def call(transaction: Transaction): Mono[Binary]
  def estimate(transaction: Transaction): Mono[BigInteger]
  def sendTransaction(transaction: Transaction): Mono[Word]
}
