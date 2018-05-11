package scalether.transaction

import java.math.BigInteger

import scala.language.higherKinds

trait GasPriceProvider[F[_]] {
  def gasPrice: F[BigInteger]
}
