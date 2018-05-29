package io.daonomic.rpc.tries

import io.daonomic.rpc.RpcTransport
import io.daonomic.rpc.domain.StatusAndBody
import scalaj.http.{Http, HttpRequest}

import scala.util.Try

class ScalajHttpTransport(baseUrl: String, connTimeoutMs: Int = 10000, readTimeoutMs: Int = 10000, f: HttpRequest => HttpRequest = t => t)
  extends RpcTransport[Try] {

  private def requestTemplate(url: String) = f(Http(baseUrl + url)
    .timeout(connTimeoutMs, readTimeoutMs)
    .header("Content-Type", "application/json"))

  override def get(url: String): Try[StatusAndBody] = Try {
    val response = requestTemplate(url).asString
    StatusAndBody(response.code, response.body)
  }

  override def post(url: String, request: String): Try[StatusAndBody] = Try {
    val response = requestTemplate(url)
      .postData(request)
      .asString
    StatusAndBody(response.code, response.body)
  }
}

object ScalajHttpTransport {
  def apply(rpcUrl: String, user: String, password: String, connTimeoutMs: Int = 10000, readTimeoutMs: Int = 10000) =
    new ScalajHttpTransport(rpcUrl, f = t => t.auth(user, password))
}