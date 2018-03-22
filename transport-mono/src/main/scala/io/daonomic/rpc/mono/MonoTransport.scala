package io.daonomic.rpc.mono

import java.nio.charset.StandardCharsets
import java.util.Base64

import io.daonomic.rpc.transport.{RpcResponse, RpcTransport}
import org.asynchttpclient.{DefaultAsyncHttpClient, RequestBuilder}
import reactor.core.publisher.Mono

class MonoTransport(rpcUrl: String, requestTimeoutMs: Int = 10000, readTimeoutMs: Int = 10000, f: RequestBuilder => RequestBuilder = t => t)
  extends RpcTransport[Mono] {

  private val client = new DefaultAsyncHttpClient()

  override def execute(request: String): Mono[RpcResponse] = {
    val req = f(new RequestBuilder())
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

object MonoTransport {
  def getBasicHeaderValue(username: String, password: String): String =
    Base64.getEncoder.encodeToString((username + ':' + password).getBytes(StandardCharsets.UTF_8))

  def apply(rpcUrl: String, user: String, password: String, requestTimeoutMs: Int = 10000, readTimeoutMs: Int = 10000): MonoTransport = {
    new MonoTransport(rpcUrl, f = t => t.addHeader("Authorization", s"Basic ${MonoTransport.getBasicHeaderValue(user, password)}"))
  }
}