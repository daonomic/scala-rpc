package io.daonomic.rpc

import io.daonomic.rpc.domain.RpcResponse

import scala.language.higherKinds

trait RpcTransport[F[_]] {
  def execute(request: String): F[RpcResponse]
}
