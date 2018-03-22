package io.daonomic.rpc.tries

import io.daonomic.rpc.ManualTag
import org.scalatest.FlatSpec

class ScalajHttpTransportSpec extends FlatSpec {
  val transport = ScalajHttpTransport("http://localhost:18332", "user", "pass")

  "ScalajHttpTransport" should "execute req with basic auth" taggedAs ManualTag in {
    val resp = transport.execute("{\"id\":1,\"method\":\"getblockchaininfo\",\"params\":[],\"jsonrpc\":\"2.0\"}").get
    assert(resp.code == 200)
  }
}
