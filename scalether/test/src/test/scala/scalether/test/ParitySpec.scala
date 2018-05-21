package scalether.test

import io.daonomic.cats.implicits._
import io.daonomic.rpc.ManualTag
import io.daonomic.rpc.domain.Word
import io.daonomic.rpc.tries.ScalajHttpTransport
import org.scalatest.FlatSpec
import scalether.core.{Ethereum, Parity}

import scala.util.Try

class ParitySpec extends FlatSpec {
  val parity = new Parity[Try](new ScalajHttpTransport("http://ether-dev:8545"))
  val ethereum = new Ethereum[Try](new ScalajHttpTransport("http://ether-dev:8545"))

  "Parity" should "get transaction trace" taggedAs ManualTag in {
    println(parity.traceTransaction("0x1825ab1f99ed41fa62f5ff324cf29eee802e6c07d3a5e21d905ff1f1add54b86").get)
  }

  "Ethereum" should "get transaction by hash" taggedAs ManualTag in {
    println(ethereum.ethGetTransactionByHash(Word("0x4442468db05a4ab2edf6a4550882d023ef1aa80cb92f33f850d21c181e6597f7")))
  }
}
