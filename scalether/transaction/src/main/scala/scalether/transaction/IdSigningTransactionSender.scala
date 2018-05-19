package scalether.transaction

import java.math.BigInteger

import cats.Id
import io.daonomic.cats.implicits._
import scalether.core.IdEthereum
import scalether.domain.Word
import scalether.domain.request.Transaction

class IdSigningTransactionSender(ethereum: IdEthereum, nonceProvider: IdNonceProvider, privateKey: BigInteger, gas: BigInteger, gasPrice: IdGasPriceProvider)
  extends SigningTransactionSender[Id](ethereum, nonceProvider, privateKey, gas, gasPrice) with IdTransactionSender {

  override def sendTransaction(transaction: Transaction): Word =
    super.sendTransaction(transaction)
}
