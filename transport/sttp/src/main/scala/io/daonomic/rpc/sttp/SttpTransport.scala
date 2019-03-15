package io.daonomic.rpc.sttp

import cats.Functor
import cats.syntax.functor._
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import com.softwaremill.sttp._
import io.daonomic.rpc.{HttpTransport, RpcTransport, domain}

import scala.concurrent.duration._
import scala.language.higherKinds

final class SttpTransport[F[_]: Functor](baseUri: Uri, mapper: ObjectMapper with ScalaObjectMapper, readTimeout: Duration = 10.seconds)(implicit backend: SttpBackend[F, _])
  extends RpcTransport[F] with HttpTransport[F] {

  private def toAbsolute(suffix: String) =
    if (suffix.isEmpty) baseUri else {
      val originalPath = baseUri.path
      val suffixPath = baseUri.path(suffix).path
      baseUri.path(originalPath ++ suffixPath)
    }

  override def send[T: Manifest](request: domain.Request): F[domain.Response[T]] =
    sttp
      .body(mapper.writeValueAsString(request))
      .contentType("application/json")
      .readTimeout(readTimeout)
      .post(toAbsolute(""))
      .send()
      .map(response => mapper.readValue[domain.Response[T]](response.body.merge))

  override def get[T: Manifest](url: String): F[T] = {
    sttp
      .readTimeout(readTimeout)
      .get(toAbsolute(url))
      .send()
      .map(response => mapper.readValue(response.body.merge))
  }
}
