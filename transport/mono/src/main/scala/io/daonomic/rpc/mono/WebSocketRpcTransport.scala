package io.daonomic.rpc.mono

import java.net.URI

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import io.daonomic.rpc.MonoRpcTransport
import io.daonomic.rpc.domain.{Request, Response}
import reactor.core.publisher.{EmitterProcessor, Mono}

class WebSocketRpcTransport(uri: String, mapper: ObjectMapper with ScalaObjectMapper) extends MonoRpcTransport {
  private val send = EmitterProcessor.create[String]()
  private val sendSink = send.sink()

  private val incoming = WebSocketClient.reconnect(new URI(uri), send)

  override def send[T: Manifest](request: Request): Mono[Response[T]] = {
    Mono.create[Response[T]] { sink =>
      sendSink.next(mapper.writeValueAsString(request))
      incoming.filter().subscribe(sink)
    }
  }
}
