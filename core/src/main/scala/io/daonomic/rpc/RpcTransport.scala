package io.daonomic.rpc

import io.daonomic.rpc.domain.StatusAndBody

import scala.language.higherKinds

trait RpcTransport[F[_]] {
  def get(url: String): F[StatusAndBody]
  def post(url: String, request: String): F[StatusAndBody]
}
