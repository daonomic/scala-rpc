package scalether.transaction

import java.math.BigInteger

import scalether.domain.Address

import scala.language.higherKinds

trait NonceProvider[F[_]] {
  def nonce(address: Address): F[BigInteger]
}
