package io.daonomic.rpc

import com.fasterxml.jackson.databind.JsonNode
import io.daonomic.rpc.domain.Response
import reactor.core.publisher.Mono

trait MonoWebSocketRpcTransport {
  def send(req: Any): Mono[Void]
  def receive(): Mono[Response[JsonNode]]
}
