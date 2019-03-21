package io.daonomic.bitcoin.rpc

import io.daonomic.bitcoin.rpc.core.{MonoBitcoind, MonoRestBitcoind}
import io.daonomic.rpc.mono.WebClientTransport

trait IntegrationSpec {
  val transport = WebClientTransport.createWithBasicAuth("http://btc:8332", "user", "pass")
  val bitcoind = new MonoBitcoind(transport)
  val restBitcoind = new MonoRestBitcoind(transport)
}
