package scalether.transaction

import java.math.BigInteger

import io.daonomic.cats.mono.implicits._
import reactor.core.publisher.Mono
import scalether.core.MonoEthereum
import scalether.domain.Address

class MonoSimpleTransactionSender(ethereum: MonoEthereum, from: Address, gas: BigInteger, gasPrice: MonoGasPriceProvider)
  extends SimpleTransactionSender[Mono](ethereum, from, gas, gasPrice) with MonoTransactionSender {
}
