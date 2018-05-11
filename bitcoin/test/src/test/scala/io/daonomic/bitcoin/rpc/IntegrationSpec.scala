package io.daonomic.bitcoin.rpc

import io.daonomic.cats.implicits._
import io.daonomic.bitcoin.rpc.core.Bitcoind
import io.daonomic.rpc.mono.WebClientTransport
import reactor.core.publisher.Mono

trait IntegrationSpec {
  val transport = WebClientTransport("http://btc-dev:18332", "user", "pass")
  val bitcoind = new Bitcoind[Mono](transport)
}
