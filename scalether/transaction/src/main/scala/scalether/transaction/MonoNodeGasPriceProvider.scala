package scalether.transaction

import reactor.core.publisher.Mono
import scalether.core.MonoEthereum

class MonoNodeGasPriceProvider(monoEthereum: MonoEthereum) extends NodeGasPriceProvider[Mono](monoEthereum) with MonoGasPriceProvider
