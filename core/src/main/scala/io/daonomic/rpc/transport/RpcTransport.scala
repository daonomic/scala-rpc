package io.daonomic.rpc.transport

import scala.language.higherKinds

trait RpcTransport[F[_]] {
  def execute(request: String): F[RpcResponse]
}

case class RpcResponse(code: Int, body: String)