package scalether.transaction

import java.math.BigInteger

import cats.Id

trait IdGasPriceProvider extends GasPriceProvider[Id] {
  def gasPrice: BigInteger
}
