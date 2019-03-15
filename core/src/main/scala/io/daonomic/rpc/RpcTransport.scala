package io.daonomic.rpc

import io.daonomic.rpc.domain.{Request, Response}

import scala.language.higherKinds

trait RpcTransport[F[_]] {
  def send[T: Manifest](request: Request): F[Response[T]]
}
