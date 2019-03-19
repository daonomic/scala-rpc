package io.daonomic.rpc

import reactor.core.publisher.Flux

trait MonoWebSocketTransport {
  def connect(send: Flux[String]): Flux[String]
}
