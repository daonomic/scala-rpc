package io.daonomic.rpc.domain

case class Request(id: Long,
                   method: String,
                   params: List[Any],
                   jsonrpc: String = "2.0")

object Request {
  def apply(id: Long, method: String, params: Any*): Request =
    new Request(id, method, params.toList)
}
