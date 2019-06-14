package scalether.test

import io.daonomic.cats.implicits._
import io.daonomic.rpc.domain.Word
import io.daonomic.rpc.tries.ScalajHttpTransport
import io.daonomic.rpc.{JsonConverter, ManualTag}
import org.scalatest.FlatSpec
import scalether.core.json.EthereumJacksonModule
import scalether.core.{Ethereum, Parity}

import scala.util.Try

class ParitySpec extends FlatSpec {
  val parity = new Parity[Try](new ScalajHttpTransport("http://ether-ropsten:8545", new JsonConverter(new EthereumJacksonModule)))
  val ethereum = new Ethereum[Try](new ScalajHttpTransport("http://ether-ropsten:8545", new JsonConverter(new EthereumJacksonModule)))

  "Parity" should "get transaction trace" taggedAs ManualTag in {
    println(parity.traceTransaction("0xad962f134127cee64c034d782d05c57eeeda5e20a8b0f078761278cdf7527829").get)
  }

  "Ethereum" should "get transaction by hash" taggedAs ManualTag in {
    println(ethereum.ethGetTransactionByHash(Word("0xad962f134127cee64c034d782d05c57eeeda5e20a8b0f078761278cdf7527829")).get)
  }

  it should "get None by hash if tx not found" taggedAs ManualTag in {
    assert(ethereum.ethGetTransactionByHash(Word("0xad962f134127cee64c034d782d05c57eeeda5e20a8b0f078761278cdf7527828")).get.isEmpty)
  }
}
