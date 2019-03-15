package io.daonomic.rpc.domain

case class Response[T](id: Long,
                       result: Option[T],
                       error: Option[Error] = None) {
  def this(id: Long, result: T) = this(id, Some(result))
}
