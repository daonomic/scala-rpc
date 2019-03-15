package io.daonomic.rpc
import java.util.concurrent.atomic.AtomicLong

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import io.daonomic.rpc.domain.{Request, Response}
import reactor.core.publisher.Mono

class RpcWebSocketTransport(transport: MonoWebSocketRpcTransport, mapper: ObjectMapper with ScalaObjectMapper)
  extends MonoRpcTransport {

  val id = new AtomicLong()

  override def send[T: Manifest](request: Request): Mono[Response[T]] = {
    val id = this.id.incrementAndGet()
    transport.receive()
      .filter { _.id == id }
      .doOnSubscribe { _ =>
        transport.send(request.copy(id = id)).subscribe()
      }
      .map { resp => Response(request.id, resp.result.map { result => mapper.convertValue[T](result) }, resp.error) }
  }
}
