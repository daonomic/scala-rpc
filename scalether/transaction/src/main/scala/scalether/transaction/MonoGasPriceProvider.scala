package scalether.transaction

import java.math.BigInteger

import reactor.core.publisher.Mono

trait MonoGasPriceProvider extends GasPriceProvider[Mono] {
  def gasPrice: Mono[BigInteger]
}
