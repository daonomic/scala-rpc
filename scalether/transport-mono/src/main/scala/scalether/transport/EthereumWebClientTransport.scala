package scalether.transport

import io.daonomic.rpc.JsonConverter
import io.daonomic.rpc.mono.WebClientTransport
import scalether.core.json.EthereumJacksonModule

object EthereumWebClientTransport {
  def apply(rpcUrl: String, requestTimeoutMs: Int = 10000, readWriteTimeoutMs: Int = 10000) =
    new WebClientTransport(rpcUrl, JsonConverter.createMapper(new EthereumJacksonModule), requestTimeoutMs, readWriteTimeoutMs)
}
