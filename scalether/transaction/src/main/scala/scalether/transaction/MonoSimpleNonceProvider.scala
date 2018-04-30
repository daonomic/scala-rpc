package scalether.transaction

import java.math.BigInteger

import reactor.core.publisher.Mono
import scalether.core.MonoEthereum
import scalether.domain.Address

class MonoSimpleNonceProvider(ethereum: MonoEthereum)
  extends SimpleNonceProvider[Mono](ethereum) with MonoNonceProvider {

  override def nonce(address: Address): Mono[BigInteger] = super.nonce(address)
}
