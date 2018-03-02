package io.daonomic.rpc.scalaj

import cats.implicits._
import io.daonomic.rpc.RpcHttpClient
import io.daonomic.rpc.json.JsonConverter
import org.scalatest.FlatSpec

class BitcoinSpec extends FlatSpec {
  val client = new RpcHttpClient(new JsonConverter(), new ScalajHttpTransport("http://localhost:18332", template = t => t.auth("user", "pass")))

  "Client" should "do something" in {
    val result = client.exec[AnyRef]("importaddress").get
  }
}
