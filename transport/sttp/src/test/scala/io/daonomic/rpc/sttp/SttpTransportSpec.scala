package io.daonomic.rpc.sttp

import com.softwaremill.sttp._
import com.softwaremill.sttp.testing.SttpBackendStub
import io.daonomic.rpc.domain.StatusAndBody
import org.scalatest.FlatSpec
import org.scalatest.prop.GeneratorDrivenPropertyChecks._

import scala.language.higherKinds

final class SttpTransportSpec extends FlatSpec {
  private val baseUri = uri"https://host.example/path"

  private def isJsonRequest[F[_]](request: RequestT[F, _, _]): Boolean =
    request.headers.exists {
      case (name, value) =>
        name.equalsIgnoreCase("content-type") &&
          value.toLowerCase.startsWith("application/json")
    }

  "SttpTransport" should "get the expected URI" in {
    forAll { responseBody: String =>
      val expectedUri = uri"https://host.example/path/subpath/end"
      implicit val backend: SttpBackend[Id, Nothing] = SttpBackendStub
        .synchronous
        .whenRequestMatches(_.uri == expectedUri)
        .thenRespond(Response.ok(responseBody))

      val transport = new SttpTransport(baseUri)
      assert(transport.get("/subpath/end").code == 200)
    }
  }

  "SttpTransport" should "post to the expected URI" in {
    forAll { (requestBody: String, responseBody: String) =>
      implicit val backend: SttpBackend[Id, Nothing] = SttpBackendStub
        .synchronous
        .whenRequestMatches(_.uri == baseUri)
        .thenRespond(Response.ok(responseBody))

      val transport = new SttpTransport(baseUri)
      assert(transport.post("", requestBody).code == 200)
    }
  }

  "SttpTransport" should "post with an appropriate content-type set" in {
    forAll { (requestBody: String, responseBody: String) =>
      implicit val backend: SttpBackend[Id, Nothing] = SttpBackendStub
        .synchronous
        .whenRequestMatchesPartial {
          case req if isJsonRequest(req) =>
            Response.ok(responseBody)
        }

      val transport = new SttpTransport(baseUri)
      assert(transport.post("", requestBody).code == 200)
    }
  }

  "SttpTransport" should "return post error bodies as expected" in {
    forAll { (requestBody: String, responseBody: String) =>
      implicit val backend: SttpBackend[Id, Nothing] = SttpBackendStub
        .synchronous
        .whenRequestMatchesPartial {
          case req =>
            Response.error(responseBody, code = 503)
        }

      val transport = new SttpTransport(baseUri)
      assert(transport.post("", requestBody).body == responseBody)
    }
  }
}
