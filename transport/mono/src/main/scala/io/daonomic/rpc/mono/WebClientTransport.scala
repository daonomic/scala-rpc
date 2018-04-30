package io.daonomic.rpc.mono

import java.nio.charset.StandardCharsets
import java.util.Base64
import java.util.concurrent.TimeUnit

import io.daonomic.rpc.MonoRpcTransport
import io.daonomic.rpc.domain.RpcResponse
import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import org.springframework.http.ResponseEntity
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

class WebClientTransport(rpcUrl: String, requestTimeoutMs: Int = 10000, readTimeoutMs: Int = 10000,
                         f: WebClient.RequestBodySpec => WebClient.RequestBodySpec = t => t)
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

  override def execute(request: String): Mono[RpcResponse] = {
    f(client.post())
      .body(BodyInserters.fromObject(request))
      .exchange()
      .flatMap[ResponseEntity[String]](resp => resp.toEntity(classOf[String]))
      .map(entity => RpcResponse(entity.getStatusCode.value(), entity.getBody))
  }
}

object WebClientTransport {
  def getBasicHeaderValue(username: String, password: String): String =
    Base64.getEncoder.encodeToString((username + ':' + password).getBytes(StandardCharsets.UTF_8))

  def apply(rpcUrl: String, user: String, password: String, requestTimeoutMs: Int = 10000, readTimeoutMs: Int = 10000): WebClientTransport = {
    new WebClientTransport(rpcUrl, f = t => t.header("Authorization", s"Basic ${WebClientTransport.getBasicHeaderValue(user, password)}"))
  }
}