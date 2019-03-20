package io.daonomic.rpc.mono

import java.net.URI

import org.slf4j.{Logger, LoggerFactory}
import reactor.core.publisher.{EmitterProcessor, Flux, ReplayProcessor}
import WebSocketReconnectingClient.logger

class WebSocketReconnectingClient(uri: String) {
  private val send = EmitterProcessor.create[String]()

  private val incoming = ReplayProcessor.create[String](0)

  {
    WebSocketClient.reconnect(new URI(uri), send).subscribe(
      s => incoming.onNext(s),
      th => logger.error("should never happen", th),
      () => logger.error("should never happen")
    )
  }

  def send(message: String): Unit = {
    send.onNext(message)
  }

  def receive(): Flux[String] = incoming
}

object WebSocketReconnectingClient {
  val logger: Logger = LoggerFactory.getLogger(WebSocketReconnectingClient.getClass)
}
