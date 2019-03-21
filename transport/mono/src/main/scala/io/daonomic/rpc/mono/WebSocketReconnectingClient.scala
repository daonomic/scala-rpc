package io.daonomic.rpc.mono

import java.net.URI

import io.daonomic.rpc.mono.WebSocketReconnectingClient.logger
import org.slf4j.{Logger, LoggerFactory}
import reactor.core.publisher.{EmitterProcessor, Flux, ReplayProcessor}

class WebSocketReconnectingClient(uri: String) {
  private val send = EmitterProcessor.create[String]()
  private val sendSink = send.sink()

  private val incoming = ReplayProcessor.create[String](0)

  {
    WebSocketClient.reconnect(new URI(uri), send).subscribe(
      s => incoming.onNext(s),
      th => logger.error("should never happen", th),
      () => logger.error("should never happen")
    )
  }

  def send(message: String): Unit = {
    sendSink.next(message)
  }

  def receive(): Flux[String] = incoming
}

object WebSocketReconnectingClient {
  val logger: Logger = LoggerFactory.getLogger(WebSocketReconnectingClient.getClass)
}
