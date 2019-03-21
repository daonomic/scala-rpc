package io.daonomic.rpc.mono

import java.net.URI
import java.time.Duration

import com.fasterxml.jackson.databind.{JsonNode, ObjectMapper}
import io.daonomic.rpc.mono.WebSocketReconnectingClient.logger
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.web.reactive.socket.adapter.NettyWebSocketSessionSupport
import reactor.core.publisher.{Flux, ReplayProcessor}

class WebSocketReconnectingClient(uri: String, val timeout: Duration = Duration.ofSeconds(5), maxFramePayloadLength: Int = NettyWebSocketSessionSupport.DEFAULT_FRAME_MAX_SIZE) {
  private val mapper = new ObjectMapper()
  private val send = ReplayProcessor.createTimeout[String](timeout)
  private val sendSink = send.sink()

  private val incoming = ReplayProcessor.create[JsonNode](0)

  {
    SimpleWebSocketClient.reconnect(new URI(uri), maxFramePayloadLength, send).subscribe(
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
