package scalether.transaction

import java.math.BigInteger

import scalether.core.Ethereum

import scala.language.higherKinds

class NodeGasPriceProvider[F[_]](ethereum: Ethereum[F]) extends GasPriceProvider[F] {
  override def gasPrice: F[BigInteger] = ethereum.ethGasPrice()
}
