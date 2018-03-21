package io.daonomic.rpc.mono

import io.daonomic.rpc.transport.{RpcResponse, RpcTransport}
import org.asynchttpclient.{DefaultAsyncHttpClient, RequestBuilder}
import reactor.core.publisher.Mono

class MonoTransport(rpcUrl: String, requestTimeoutMs: Int = 10000, readTimeoutMs: Int = 10000)
  extends RpcTransport[Mono] {

  private val client = new DefaultAsyncHttpClient()

  override def execute(request: String): Mono[RpcResponse] = {
    val req = new RequestBuilder()
      .setReadTimeout(readTimeoutMs)
      .setRequestTimeout(requestTimeoutMs)
      .setUrl(rpcUrl)
      .setBody(request)
      .addHeader("Content-Type", "application/json")
      .setMethod("POST")
    Mono.defer(() => Mono.fromFuture(client.executeRequest(req).toCompletableFuture
      .thenApply(resp => RpcResponse(resp.getStatusCode, resp.getResponseBody))))
  }
}
