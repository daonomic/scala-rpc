package scalether.listener

import cats.Id
import io.daonomic.blockchain.IdBlockchain
import io.daonomic.cats.implicits._
import scalether.core.{IdEthereum, IdParity}

class IdEthereumBlockchain(ethereum: IdEthereum, parity: IdParity)
  extends EthereumBlockchain[Id](ethereum, parity) with IdBlockchain {

}
