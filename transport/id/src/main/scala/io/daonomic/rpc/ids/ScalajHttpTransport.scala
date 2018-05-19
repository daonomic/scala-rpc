package io.daonomic.rpc.ids

import io.daonomic.rpc.domain.RpcResponse
import io.daonomic.rpc.{IdRpcTransport, RpcIoException}
import scalaj.http.{Http, HttpRequest}

class ScalajHttpTransport(rpcUrl: String, connTimeoutMs: Int = 10000, readTimeoutMs: Int = 10000, f: HttpRequest => HttpRequest = t => t)
  extends IdRpcTransport {

  private val requestTemplate = f(Http(rpcUrl)
    .timeout(connTimeoutMs, readTimeoutMs)
    .header("Content-Type", "application/json"))

  @throws[RpcIoException]
  override def execute(request: String): RpcResponse = try {
    val response = requestTemplate
      .postData(request)
      .asString
    RpcResponse(response.code, response.body)
  } catch {
    case e:Throwable => throw new RpcIoException(e)
  }
}

object ScalajHttpTransport {
  def apply(rpcUrl: String, user: String, password: String, connTimeoutMs: Int = 10000, readTimeoutMs: Int = 10000) =
    new ScalajHttpTransport(rpcUrl, f = t => t.auth(user, password))
}