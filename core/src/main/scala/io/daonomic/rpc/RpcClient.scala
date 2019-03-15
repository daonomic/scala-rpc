package io.daonomic.rpc

import java.util.concurrent.atomic.AtomicInteger

import cats.implicits._
import io.daonomic.cats.MonadThrowable
import io.daonomic.rpc.domain.{Error, Request, Response}

import scala.language.higherKinds
import scala.reflect.Manifest

class RpcClient[F[_]](transport: RpcTransport[F])
                     (implicit me: MonadThrowable[F]) {

  def exec[T](method: String, params: Any*)
             (implicit mf: Manifest[T]): F[T] = {
    execOption[T](method, params: _*).flatMap {
      case Some(v) => me.pure(v)
      case None => me.raiseError(new RpcCodeException(s"no result provided, method: $method params: $params", Error.default))
    }
  }

  def execOption[T](method: String, params: Any*)
                   (implicit mf: Manifest[T]): F[Option[T]] = {
    executeRaw[T](Request(RpcClient.id.incrementAndGet(), method, params: _*)).flatMap {
      response =>
        response.error match {
          case Some(r) => me.raiseError(new RpcCodeException(s"error caught. method: $method params: $params", r))
          case None => me.pure(response.result)
        }
    }
  }

  def executeRaw[T: Manifest](request: Request): F[Response[T]] = {
    transport.send(request)
  }
}

object RpcClient {
  val id = new AtomicInteger()
}
