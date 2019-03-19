package io.daonomic.rpc.mono

import java.net.URI

import io.daonomic.rpc.MonoWebSocketTransport
import reactor.core.publisher._

class WebSocketTransport(uri: String)
  extends MonoWebSocketTransport {

  override def connect(send: Flux[String]): Flux[String] =
    WebSocketClient.connect(new URI(uri), send)
}
