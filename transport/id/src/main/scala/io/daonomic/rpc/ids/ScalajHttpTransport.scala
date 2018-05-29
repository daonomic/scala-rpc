package io.daonomic.rpc.ids

import cats.Id
import io.daonomic.rpc.domain.StatusAndBody
import io.daonomic.rpc.{IdRpcTransport, RpcIoException}
import scalaj.http.{Http, HttpRequest}

class ScalajHttpTransport(baseUrl: String, connTimeoutMs: Int = 10000, readTimeoutMs: Int = 10000, f: HttpRequest => HttpRequest = t => t)
  extends IdRpcTransport {

  private def requestTemplate(url: String) = f(Http(baseUrl + url)
    .timeout(connTimeoutMs, readTimeoutMs)
    .header("Content-Type", "application/json"))

  @throws[RpcIoException]
  override def post(url: String, request: String): StatusAndBody = try {
    val response = requestTemplate(url)
      .postData(request)
      .asString
    StatusAndBody(response.code, response.body)
  } catch {
    case e: Throwable => throw new RpcIoException(e)
  }

  @throws[RpcIoException]
  override def get(url: String): Id[StatusAndBody] = try {
    val response = requestTemplate(url)
      .asString
    StatusAndBody(response.code, response.body)
  } catch {
    case e: Throwable => throw new RpcIoException(e)
  }
}

object ScalajHttpTransport {
  def apply(rpcUrl: String, user: String, password: String, connTimeoutMs: Int = 10000, readTimeoutMs: Int = 10000) =
    new ScalajHttpTransport(rpcUrl, f = t => t.auth(user, password))
}