package scalether.transaction

import java.math.BigInteger

import cats.Id
import io.daonomic.cats.implicits._
import io.daonomic.rpc.domain.{Binary, Word}
import scalether.core.IdEthereum
import scalether.domain.request.Transaction
import scalether.sync.{IdSynchronizer, SemaphoreIdSynchronizer}

class IdSigningTransactionSender(override val ethereum: IdEthereum, nonceProvider: IdNonceProvider, synchronizer: IdSynchronizer, privateKey: BigInteger, gas: BigInteger, gasPrice: IdGasPriceProvider)
  extends SigningTransactionSender[Id](ethereum, nonceProvider, synchronizer, privateKey, gas, gasPrice) with IdTransactionSender {

  def this(ethereum: IdEthereum, nonceProvider: IdNonceProvider, privateKey: BigInteger, gas: BigInteger, gasPrice: IdGasPriceProvider) {
    this(ethereum, nonceProvider, new SemaphoreIdSynchronizer(), privateKey, gas, gasPrice)
  }

  override def sendTransaction(transaction: Transaction): Word =
    super.sendTransaction(transaction)

  override def prepare(transaction: Transaction): Transaction =
    super.prepare(transaction)

  override def getRawTransaction(transaction: Transaction): Binary =
    super.getRawTransaction(transaction)
}
