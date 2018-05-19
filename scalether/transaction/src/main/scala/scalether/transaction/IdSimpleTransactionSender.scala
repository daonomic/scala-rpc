package scalether.transaction

import java.math.BigInteger

import cats.Id
import io.daonomic.cats.implicits._
import scalether.core.IdEthereum
import scalether.domain.Address

class IdSimpleTransactionSender(ethereum: IdEthereum, from: Address, gas: BigInteger, gasPrice: IdGasPriceProvider)
  extends SimpleTransactionSender[Id](ethereum, from, gas, gasPrice) with IdTransactionSender {
}
