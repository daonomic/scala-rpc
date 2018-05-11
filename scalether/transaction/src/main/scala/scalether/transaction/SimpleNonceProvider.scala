package scalether.transaction

import java.math.BigInteger

import scalether.core.Ethereum
import scalether.domain.Address

import scala.language.higherKinds

class SimpleNonceProvider[F[_]](ethereum: Ethereum[F]) extends NonceProvider[F] {

  def nonce(address: Address): F[BigInteger] = ethereum.ethGetTransactionCount(address, "pending")
}
