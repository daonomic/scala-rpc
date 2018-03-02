package io.daonomic.rpc.domain

case class Response[T](id: Long,
                       result: Option[T],
                       error: Option[Error])
