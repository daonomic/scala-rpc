package scalether.transaction

import java.math.BigInteger

import cats.Monad

import scala.language.higherKinds

class ValGasPriceProvider[F[_]](value: BigInteger)(implicit m: Monad[F]) extends GasPriceProvider[F] {
  override def gasPrice: F[BigInteger] = m.pure(value)
}
