package io.daonomic.rpc.sttp

import com.softwaremill.sttp._
import com.softwaremill.sttp.testing.SttpBackendStub
import io.daonomic.rpc.{JsonConverter, domain}
import io.daonomic.rpc.domain.Request
import org.scalatest.FlatSpec
import org.scalatest.prop.GeneratorDrivenPropertyChecks._

import scala.language.higherKinds

final class SttpTransportSpec extends FlatSpec {
  private val baseUri = uri"https://host.example/path"
  private val jsonConverter = new JsonConverter

  private def isJsonRequest[F[_]](request: RequestT[F, _, _]): Boolean =
    request.headers.exists {
      case (name, value) =>
        name.equalsIgnoreCase("content-type") &&
          value.toLowerCase.startsWith("application/json")
    }

  "get" should "hit the expected URI and return value" in {
    forAll { responseBody: String =>
      val expectedUri = uri"https://host.example/path/subpath/end"
      implicit val backend: SttpBackend[Id, Nothing] = SttpBackendStub
        .synchronous
        .whenRequestMatches(_.uri == expectedUri)
        .thenRespond(Response.ok(jsonConverter.toJson(StringResponse(responseBody))))

      val transport = new SttpTransport(baseUri, JsonConverter.createMapper())
      assert(transport.get[StringResponse]("/subpath/end") == StringResponse(responseBody))
    }
  }

  "send" should "send request and get response" in {
    forAll { (requestBody: String, responseBody: String) =>
      implicit val backend: SttpBackend[Id, Nothing] = SttpBackendStub
        .synchronous
        .whenRequestMatches(_.uri == baseUri)
        .thenRespond(Response.ok(jsonConverter.toJson(new domain.Response(1, responseBody))))

      val transport = new SttpTransport(baseUri, JsonConverter.createMapper())
      assert(transport.send[String](Request.apply(1, "test", requestBody)).result.get == responseBody)
    }
  }
  it should "make a request with an appropriate content-type set" in {
    forAll { (requestBody: String, responseBody: String) =>
      implicit val backend: SttpBackend[Id, Nothing] = SttpBackendStub
        .synchronous
        .whenRequestMatchesPartial {
          case req if isJsonRequest(req) =>
            Response.ok(jsonConverter.toJson(new domain.Response(1, responseBody)))
        }

      val transport = new SttpTransport(baseUri, JsonConverter.createMapper())
      assert(transport.send[String](Request.apply(1, "test", requestBody)).result.get == responseBody)
    }
  }

  it should "return error bodies as expected" in {
    forAll { (requestBody: String, responseBody: String) =>
      implicit val backend: SttpBackend[Id, Nothing] = SttpBackendStub
        .synchronous
        .whenRequestMatchesPartial {
          case req =>
            Response.error(jsonConverter.toJson(new domain.Response(1, responseBody)), code = 503)
        }

      val transport = new SttpTransport(baseUri, JsonConverter.createMapper())
      assert(transport.send[String](Request.apply(1, "test", requestBody)).result.get == responseBody)
    }
  }
}

case class StringResponse(value: String)