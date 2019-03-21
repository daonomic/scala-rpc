package io.daonomic.rpc.mono

import java.util.concurrent.atomic.AtomicLong

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import io.daonomic.rpc.MonoRpcTransport
import io.daonomic.rpc.domain.{Request, Response}
import reactor.core.publisher.Mono

class WebSocketRpcTransport(client: WebSocketReconnectingClient, mapper: ObjectMapper with ScalaObjectMapper) extends MonoRpcTransport {

  val id = new AtomicLong()

  override def send[T: Manifest](request: Request): Mono[Response[T]] = {
    val id = this.id.incrementAndGet()
    client.receive()
      .filter(response => response.get("id").longValue() == id)
      .map[Response[T]](response => mapper.convertValue[Response[T]](response))
      .map[Response[T]](response => response.copy(id = request.id))
      .next()
      .doOnSubscribe(_ => client.send(mapper.writeValueAsString(request.copy(id = id))))
      .timeout(client.timeout)
  }
}
