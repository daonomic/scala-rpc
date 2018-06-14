package io.daonomic.rpc

import cats.implicits._
import io.daonomic.cats.MonadThrowable
import io.daonomic.rpc.domain.{Error, Request, Response, StatusAndBody}
import org.slf4j.{Logger, LoggerFactory}

import scala.language.higherKinds
import scala.reflect.Manifest

class RpcHttpClient[F[_]](jsonConverter: JsonConverter, transport: RpcTransport[F])
                         (implicit me: MonadThrowable[F]) {

  private val logger: Logger = LoggerFactory.getLogger(getClass)

  def get[T <: AnyRef](url: String)
                      (implicit mf: Manifest[T]): F[T] = {
    if (logger.isDebugEnabled()) {
      logger.debug(s"get $url")
    }
    transport.get(url).flatMap(response => parseResponse(response, url))
  }

  def exec[T](method: String, params: Any*)
             (implicit mf: Manifest[T]): F[T] = {
    execOption[T](method, params: _*).flatMap {
      case Some(v) => me.pure(v)
      case None => me.raiseError(new RpcCodeException(Error.default))
    }
  }

  def execOption[T](method: String, params: Any*)
                   (implicit mf: Manifest[T]): F[Option[T]] = {
    execute[T](Request(1, method, params: _*)).flatMap {
      response =>
        response.error match {
          case Some(r) => me.raiseError(new RpcCodeException(r))
          case None => me.pure(response.result)
        }
    }
  }

  private def execute[T](request: Request)
                        (implicit mf: Manifest[T]): F[Response[T]] = {
    val requestJson = jsonConverter.toJson(request)
    if (logger.isDebugEnabled) {
      logger.debug(s"request=$requestJson")
    }
    transport.post("", requestJson)
      .flatMap(resp => parseResponse(resp, requestJson))
  }

  private def parseResponse[T <: AnyRef](response: StatusAndBody, request: String)(implicit mf: Manifest[T]): F[T] = {
    if (logger.isDebugEnabled()) {
      logger.debug(s"response=${response.body}")
    }
    try {
      me.pure(jsonConverter.fromJson[T](response.body))
    } catch {
      case e: Throwable => me.raiseError(new IllegalArgumentException(s"unable to parse response json. http status code=${response.code} request=$request", e))
    }
  }
}
