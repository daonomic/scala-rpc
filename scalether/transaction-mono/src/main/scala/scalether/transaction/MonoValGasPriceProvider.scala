package scalether.transaction

import java.math.BigInteger

import io.daonomic.cats.mono.implicits._
import reactor.core.publisher.Mono

class MonoValGasPriceProvider(value: BigInteger) extends ValGasPriceProvider[Mono](value) with MonoGasPriceProvider
