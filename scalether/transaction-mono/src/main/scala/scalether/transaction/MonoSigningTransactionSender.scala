package scalether.transaction

import java.math.BigInteger

import io.daonomic.cats.mono.implicits._
import io.daonomic.rpc.domain.{Binary, Word}
import reactor.core.publisher.Mono
import scalether.core.MonoEthereum
import scalether.domain.request.Transaction
import scalether.sync.{MonoSynchronizer, SemaphoreMonoSynchronizer}

class MonoSigningTransactionSender(override val ethereum: MonoEthereum, nonceProvider: MonoNonceProvider, synchronizer: MonoSynchronizer, privateKey: BigInteger, gas: BigInteger, gasPrice: MonoGasPriceProvider)
  extends SigningTransactionSender[Mono](ethereum, nonceProvider, synchronizer, privateKey, gas, gasPrice) with MonoTransactionSender {

  def this(ethereum: MonoEthereum, nonceProvider: MonoNonceProvider, privateKey: BigInteger, gas: BigInteger, gasPrice: MonoGasPriceProvider) {
    this(ethereum, nonceProvider, new SemaphoreMonoSynchronizer(), privateKey, gas, gasPrice)
  }

  override def sendTransaction(transaction: Transaction): Mono[Word] =
    super.sendTransaction(transaction)

  override def prepare(transaction: Transaction): Mono[Transaction] =
    super.prepare(transaction)

  override def getRawTransaction(transaction: Transaction): Mono[Binary] =
    super.getRawTransaction(transaction)
}
