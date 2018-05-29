package io.daonomic.rpc.mono

import java.nio.charset.StandardCharsets
import java.util.Base64
import java.util.concurrent.TimeUnit

import io.daonomic.rpc.MonoRpcTransport
import io.daonomic.rpc.domain.StatusAndBody
import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import org.springframework.http.ResponseEntity
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec
import reactor.core.publisher.Mono

class WebClientTransport(rpcUrl: String, requestTimeoutMs: Int = 10000, readTimeoutMs: Int = 10000,
                         f: RequestHeadersSpec[_] => RequestHeadersSpec[_] = t => t)
  extends MonoRpcTransport {

  private val connector = new ReactorClientHttpConnector(options => {
    options.option[Integer](ChannelOption.CONNECT_TIMEOUT_MILLIS, requestTimeoutMs)
      .compression(true)
      .afterNettyContextInit(ctx => ctx.addHandlerLast(new ReadTimeoutHandler(readTimeoutMs, TimeUnit.MILLISECONDS)))
  })

  private val client = WebClient.builder()
    .baseUrl(rpcUrl)
    .clientConnector(connector)
    .defaultHeader("Content-Type", "application/json")
    .build()

  override def get(url: String): Mono[StatusAndBody] = {
    f(client.get().uri(url))
      .exchange()
      .flatMap[ResponseEntity[String]](resp => resp.toEntity(classOf[String]))
      .map(entity => StatusAndBody(entity.getStatusCode.value(), entity.getBody))
  }

  override def post(url: String, request: String): Mono[StatusAndBody] = {
    f(client.post().uri(url)
      .body(BodyInserters.fromObject(request)))
      .exchange()
      .flatMap[ResponseEntity[String]](resp => resp.toEntity(classOf[String]))
      .map(entity => StatusAndBody(entity.getStatusCode.value(), entity.getBody))
  }
}

object WebClientTransport {
  def getBasicHeaderValue(username: String, password: String): String =
    Base64.getEncoder.encodeToString((username + ':' + password).getBytes(StandardCharsets.UTF_8))

  def apply(rpcUrl: String, user: String, password: String, requestTimeoutMs: Int = 10000, readTimeoutMs: Int = 10000): WebClientTransport = {
    new WebClientTransport(rpcUrl, f = t => t.header("Authorization", s"Basic ${WebClientTransport.getBasicHeaderValue(user, password)}").asInstanceOf[RequestHeadersSpec[_]])
  }
}