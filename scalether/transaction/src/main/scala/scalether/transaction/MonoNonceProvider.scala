package scalether.transaction

import java.math.BigInteger

import reactor.core.publisher.Mono
import scalether.domain.Address

trait MonoNonceProvider extends NonceProvider[Mono] {
  def nonce(address: Address): Mono[BigInteger]
}
