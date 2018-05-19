package scalether.transaction

import cats.Id
import scalether.core.IdEthereum

class IdNodeGasPriceProvider(ethereum: IdEthereum) extends NodeGasPriceProvider[Id](ethereum) with IdGasPriceProvider
