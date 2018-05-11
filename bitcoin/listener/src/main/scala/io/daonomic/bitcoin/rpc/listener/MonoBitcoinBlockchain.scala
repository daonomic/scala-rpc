package io.daonomic.bitcoin.rpc.listener

import io.daonomic.bitcoin.rpc.core.MonoBitcoind
import io.daonomic.blockchain.MonoBlockchain
import io.daonomic.cats.implicits._
import reactor.core.publisher.Mono

class MonoBitcoinBlockchain(bitcoind: MonoBitcoind)
  extends BitcoinBlockchain[Mono](bitcoind) with MonoBlockchain {

}
