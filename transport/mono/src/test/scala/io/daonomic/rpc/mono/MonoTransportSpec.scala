package io.daonomic.rpc.mono

import io.daonomic.rpc.ManualTag
import org.scalatest.FlatSpec

class MonoTransportSpec extends FlatSpec {
  val transport = MonoTransport("http://localhost:18332", "user", "pass")

  "MonoTransport" should "execute req with basic auth" taggedAs ManualTag in {
    val resp = transport.execute("{\"id\":1,\"method\":\"getblockchaininfo\",\"params\":[],\"jsonrpc\":\"2.0\"}").block()
    assert(resp.code == 200)
    println(resp)
  }
}
