package io.daonomic.rpc.scalaj

import io.daonomic.rpc.transport.{RpcResponse, RpcTransport}
import scalaj.http.{Http, HttpRequest}

import scala.util.Try

class ScalajHttpTransport(rpcUrl: String, connTimeoutMs: Int = 10000, readTimeoutMs: Int = 10000, template: HttpRequest => HttpRequest = t => t) extends RpcTransport[Try] {

  private val requestTemplate = template(Http(rpcUrl)
    .timeout(connTimeoutMs, readTimeoutMs)
    .header("Content-Type", "application/json"))

  override def execute(request: String): Try[RpcResponse] = Try {
    val response = requestTemplate
      .postData(request)
      .asString
    RpcResponse(response.code, response.body)
  }
}
