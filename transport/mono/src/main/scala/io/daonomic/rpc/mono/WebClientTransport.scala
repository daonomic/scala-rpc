package io.daonomic.rpc.mono

import java.nio.charset.StandardCharsets
import java.util.Base64
import java.util.concurrent.TimeUnit

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import io.daonomic.rpc.domain.{Request, Response}
import io.daonomic.rpc.{JsonConverter, MonoHttpTransport, MonoRpcTransport}
import io.netty.channel.ChannelOption
import io.netty.handler.timeout.{ReadTimeoutHandler, WriteTimeoutHandler}
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.MediaType
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.http.codec.json.{Jackson2JsonDecoder, Jackson2JsonEncoder}
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.{ExchangeStrategies, WebClient}
import reactor.core.publisher.Mono
import reactor.netty.http.client.HttpClient
import reactor.netty.tcp.TcpClient

class WebClientTransport(rpcUrl: String, mapper: ObjectMapper with ScalaObjectMapper, requestTimeoutMs: Int = 10000, readWriteTimeoutMs: Int = 10000,
                            headers: Map[String, String] = Map())
  extends MonoRpcTransport with MonoHttpTransport {

  private val client = buildClient()

  private def buildClient() = {
    val tcpClient = TcpClient.create()
      .option[Integer](ChannelOption.CONNECT_TIMEOUT_MILLIS, requestTimeoutMs)
      .doOnConnected { conn =>
        conn
          .addHandlerLast(new ReadTimeoutHandler(readWriteTimeoutMs, TimeUnit.MILLISECONDS))
          .addHandlerLast(new WriteTimeoutHandler(readWriteTimeoutMs, TimeUnit.MILLISECONDS))
      }
    val connector = new ReactorClientHttpConnector(HttpClient.from(tcpClient))
    val exchangeStrategies = ExchangeStrategies.builder()
      .codecs { configurer =>
        configurer.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(mapper, MediaType.APPLICATION_JSON))
        configurer.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder(mapper, MediaType.APPLICATION_JSON))
      }
      .build()

    val builder = WebClient.builder()
      .baseUrl(rpcUrl)
      .clientConnector(connector)
      .exchangeStrategies(exchangeStrategies)
    headers.foreach {
      case (k, v) => builder.defaultHeader(k, v)
    }
    builder.build()
  }

  override def send[T: Manifest](request: Request): Mono[Response[T]] = {
    val javaType = mapper.constructType[Response[T]]
    client.post()
      .body(BodyInserters.fromObject(request))
      .retrieve()
      .bodyToMono(ParameterizedTypeReference.forType[Response[T]](javaType))
  }

  override def get[T: Manifest](url: String): Mono[T] = {
    val javaType = mapper.constructType[T]
    client.get()
      .uri(url)
      .retrieve()
      .bodyToMono(ParameterizedTypeReference.forType[T](javaType))
  }
}

object WebClientTransport {
  def getBasicHeaderValue(username: String, password: String): String =
    Base64.getEncoder.encodeToString((username + ':' + password).getBytes(StandardCharsets.UTF_8))

  def apply(rpcUrl: String, user: String, password: String, mapper: ObjectMapper with ScalaObjectMapper, requestTimeoutMs: Int = 10000, readTimeoutMs: Int = 10000): WebClientTransport = {
    new WebClientTransport(rpcUrl, mapper, headers = Map("Authorization" -> s"Basic ${WebClientTransport.getBasicHeaderValue(user, password)}"))
  }
}