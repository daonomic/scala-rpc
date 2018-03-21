package io.daonomic.rpc

import cats.MonadError
import cats.implicits._
import io.daonomic.rpc.domain.{Error, Request, Response}
import io.daonomic.rpc.json.JsonConverter
import io.daonomic.rpc.transport.RpcTransport
import org.slf4j.{Logger, LoggerFactory}

import scala.language.higherKinds
import scala.reflect.Manifest

class RpcHttpClient[F[_]](jsonConverter: JsonConverter, transport: RpcTransport[F])
                         (implicit me: MonadError[F, Throwable]) {

  private val logger: Logger = LoggerFactory.getLogger(getClass)

  def exec[T](method: String, params: Any*)
             (implicit mf: Manifest[T]): F[T] = {
    execOption[T](method, params: _*).flatMap {
      case Some(v) => me.pure(v)
      case None => me.raiseError(new RpcException(Error.default))
    }
  }

  def execOption[T](method: String, params: Any*)
                   (implicit mf: Manifest[T]): F[Option[T]] = {
    execute[T](Request(1, method, params: _*)).flatMap {
      response =>
        response.error match {
          case Some(r) => me.raiseError(new RpcException(r))
          case None => me.pure(response.result)
        }
    }
  }

  private def execute[T](request: Request)(implicit mf: Manifest[T]): F[Response[T]] = {
    val requestJson = jsonConverter.toJson(request)
    if (logger.isDebugEnabled) {
      logger.debug(s"request=$requestJson")
    }
    transport.execute(requestJson).flatMap(response => {
      if (logger.isDebugEnabled()) {
        logger.debug(s"response=${response.body}")
      }
      try {
        me.pure(jsonConverter.fromJson[Response[T]](response.body))
      } catch {
        case e: Throwable => me.raiseError(new IllegalArgumentException(s"unable to parse response json. http status code=${response.code}", e))
      }
    })
  }
}
