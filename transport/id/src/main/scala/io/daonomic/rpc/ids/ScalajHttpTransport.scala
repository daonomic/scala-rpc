package io.daonomic.rpc.ids

import cats.Id
import io.daonomic.rpc.domain.{Request, Response, StatusAndBody}
import io.daonomic.rpc.{IdRpcTransport, JsonConverter, RpcIoException}
import scalaj.http.{Http, HttpRequest}

import scala.reflect.Manifest

class ScalajHttpTransport(baseUrl: String, jsonConverter: JsonConverter, connTimeoutMs: Int = 10000, readTimeoutMs: Int = 10000, f: HttpRequest => HttpRequest = t => t)
  extends IdRpcTransport {

  private def requestTemplate(url: String) = f(Http(baseUrl + url)
    .timeout(connTimeoutMs, readTimeoutMs)
    .header("Content-Type", "application/json"))

  @throws[RpcIoException]
  def post(url: String, request: String): StatusAndBody = try {
    val response = requestTemplate(url)
      .postData(request)
      .asString
    StatusAndBody(response.code, response.body)
  } catch {
    case e: Throwable => throw new RpcIoException(e)
  }

  @throws[RpcIoException]
  def get(url: String): Id[StatusAndBody] = try {
    val response = requestTemplate(url)
      .asString
    StatusAndBody(response.code, response.body)
  } catch {
    case e: Throwable => throw new RpcIoException(e)
  }

  override def send[T: Manifest](request: Request): Id[Response[T]] = {
    val response = post("", jsonConverter.toJson(request))
    parseResponse[Response[T]](response, request)
  }

  @throws[IllegalArgumentException]
  private def parseResponse[T: Manifest](response: StatusAndBody, request: Request): T = {
    try {
      jsonConverter.fromJson[T](response.body)
    } catch {
      case e: Throwable => throw new IllegalArgumentException(s"unable to parse response json. http status code=${response.code} request=$request", e)
    }
  }
}

object ScalajHttpTransport {
  def apply(rpcUrl: String, user: String, password: String, jsonConverter: JsonConverter, connTimeoutMs: Int = 10000, readTimeoutMs: Int = 10000) =
    new ScalajHttpTransport(rpcUrl, jsonConverter = jsonConverter, f = t => t.auth(user, password))
}