package io.daonomic.rpc.sttp

import cats.Functor
import cats.syntax.functor._
import com.softwaremill.sttp._
import io.daonomic.rpc.RpcTransport
import io.daonomic.rpc.domain.StatusAndBody

import scala.concurrent.duration._
import scala.language.higherKinds

final class SttpTransport[F[_]: Functor](baseUri: Uri, readTimeout: Duration = 10.seconds)(implicit backend: SttpBackend[F, _])
  extends RpcTransport[F] {

  private def toAbsolute(suffix: String) =
    if (suffix.isEmpty) baseUri else {
      val originalPath = baseUri.path
      val suffixPath = baseUri.path(suffix).path
      baseUri.path(originalPath ++ suffixPath)
    }

  override def get(url: String): F[StatusAndBody] =
    sttp
      .readTimeout(readTimeout)
      .get(toAbsolute(url))
      .send()
      .map(response => StatusAndBody(response.code, response.body.merge))

  override def post(url: String, request: String): F[StatusAndBody] =
    sttp
      .body(request)
      .contentType("application/json")
      .readTimeout(readTimeout)
      .post(toAbsolute(url))
      .send()
      .map(response => StatusAndBody(response.code, response.body.merge))
}
