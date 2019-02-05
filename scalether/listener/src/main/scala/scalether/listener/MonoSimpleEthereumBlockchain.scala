package scalether.listener

import io.daonomic.blockchain.MonoBlockchain
import io.daonomic.cats.implicits._
import reactor.core.publisher.Mono
import scalether.core.MonoEthereum

class MonoSimpleEthereumBlockchain(ethereum: MonoEthereum)
  extends SimpleEthereumBlockchain[Mono](ethereum) with MonoBlockchain {

}
