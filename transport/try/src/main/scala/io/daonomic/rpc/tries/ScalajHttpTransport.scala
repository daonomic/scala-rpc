package io.daonomic.rpc.tries

import io.daonomic.rpc.domain.{Request, Response, StatusAndBody}
import io.daonomic.rpc.{JsonConverter, RpcTransport}
import scalaj.http.{Http, HttpRequest}

import scala.reflect.Manifest
import scala.util.{Failure, Success, Try}

class ScalajHttpTransport(baseUrl: String, jsonConverter: JsonConverter, connTimeoutMs: Int = 10000, readTimeoutMs: Int = 10000, f: HttpRequest => HttpRequest = t => t)
  extends RpcTransport[Try] {

  private def requestTemplate(url: String) = f(Http(baseUrl + url)
    .timeout(connTimeoutMs, readTimeoutMs)
    .header("Content-Type", "application/json"))

  def get(url: String): Try[StatusAndBody] = Try {
    val response = requestTemplate(url).asString
    StatusAndBody(response.code, response.body)
  }

  def post(url: String, request: String): Try[StatusAndBody] = Try {
    val response = requestTemplate(url)
      .postData(request)
      .asString
    StatusAndBody(response.code, response.body)
  }

  override def send[T: Manifest](request: Request): Try[Response[T]] = {
    post("", jsonConverter.toJson(request))
      .flatMap(sb => parseResponse[Response[T]](sb, request))
  }

  private def parseResponse[T: Manifest](response: StatusAndBody, request: Request): Try[T] = {
    try {
      Success(jsonConverter.fromJson[T](response.body))
    } catch {
      case e: Throwable => Failure(new IllegalArgumentException(s"unable to parse response json. http status code=${response.code} request=$request", e))
    }
  }
}

object ScalajHttpTransport {
  def apply(rpcUrl: String, user: String, password: String, jsonConverter: JsonConverter, connTimeoutMs: Int = 10000, readTimeoutMs: Int = 10000) =
    new ScalajHttpTransport(rpcUrl, jsonConverter, f = t => t.auth(user, password))
}