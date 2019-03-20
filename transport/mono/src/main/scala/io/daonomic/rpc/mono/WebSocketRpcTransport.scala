package io.daonomic.rpc.mono

import java.time.Duration
import java.util.concurrent.atomic.AtomicLong

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import io.daonomic.rpc.MonoRpcTransport
import io.daonomic.rpc.domain.{Request, Response}
import reactor.core.publisher.Mono

class WebSocketRpcTransport(client: WebSocketReconnectingClient, mapper: ObjectMapper with ScalaObjectMapper, timeout: Duration = Duration.ofSeconds(10)) extends MonoRpcTransport {

  val id = new AtomicLong()

  override def send[T: Manifest](request: Request): Mono[Response[T]] = {
    val id = this.id.incrementAndGet()
    client.receive()
      .map[Response[T]](response => mapper.readValue[Response[T]](response))
      .filter(response => response.id == id)
      .map[Response[T]](response => response.copy(id = request.id))
      .next()
      .doOnSubscribe(_ => client.send(mapper.writeValueAsString(request.copy(id = id))))
      .timeout(timeout)
  }
}
