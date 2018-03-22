package io.daonomic.rpc.tries

import io.daonomic.rpc.transport.{RpcResponse, RpcTransport}
import scalaj.http.{Http, HttpRequest}

import scala.util.Try

class ScalajHttpTransport(rpcUrl: String, connTimeoutMs: Int = 10000, readTimeoutMs: Int = 10000, f: HttpRequest => HttpRequest = t => t)
  extends RpcTransport[Try] {

  private val requestTemplate = f(Http(rpcUrl)
    .timeout(connTimeoutMs, readTimeoutMs)
    .header("Content-Type", "application/json"))

  override def execute(request: String): Try[RpcResponse] = Try {
    val response = requestTemplate
      .postData(request)
      .asString
    RpcResponse(response.code, response.body)
  }
}

object ScalajHttpTransport {
  def apply(rpcUrl: String, user: String, password: String, connTimeoutMs: Int = 10000, readTimeoutMs: Int = 10000) =
    new ScalajHttpTransport(rpcUrl, f = t => t.auth(user, password))
}