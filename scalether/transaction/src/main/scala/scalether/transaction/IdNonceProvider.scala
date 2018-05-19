package scalether.transaction

import java.math.BigInteger

import cats.Id
import scalether.domain.Address

trait IdNonceProvider extends NonceProvider[Id] {
  def nonce(address: Address): BigInteger
}
