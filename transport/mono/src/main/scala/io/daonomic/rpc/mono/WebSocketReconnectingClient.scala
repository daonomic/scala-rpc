package io.daonomic.rpc.mono

import java.net.URI

import com.fasterxml.jackson.databind.{JsonNode, ObjectMapper}
import io.daonomic.rpc.mono.WebSocketReconnectingClient.logger
import org.slf4j.{Logger, LoggerFactory}
import reactor.core.publisher.{EmitterProcessor, Flux, ReplayProcessor}

class WebSocketReconnectingClient(uri: String) {
  private val mapper = new ObjectMapper()
  private val send = EmitterProcessor.create[String]()
  private val sendSink = send.sink()

  private val incoming = ReplayProcessor.create[JsonNode](0)

  {
    WebSocketClient.reconnect(new URI(uri), send).subscribe(
      s => incoming.onNext(mapper.readTree(s)),
      th => logger.error("should never happen", th),
      () => logger.error("should never happen")
    )
  }

  def send(message: String): Unit = {
    sendSink.next(message)
  }

  def receive(): Flux[JsonNode] = incoming
}

object WebSocketReconnectingClient {
  val logger: Logger = LoggerFactory.getLogger(WebSocketReconnectingClient.getClass)
}
