package scalether.generator.domain

import com.fasterxml.jackson.databind.ObjectMapper
import org.scalatest.FlatSpec
import scalether.generator.ContractGenerator

import scala.io.Source

class TruffleContractSpec extends FlatSpec {
  val json: ObjectMapper = ContractGenerator.createMapper()

  "TruffleContract" should "be deserialized" in {
    val token = Source.fromResource("IssuedToken.json").mkString
    val truffle = json.readValue(token, classOf[TruffleContract])
    assert(truffle.getAbi.size() == 14)
    assert(truffle.getName == "IssuedToken")
    assert(truffle.getBin != null)
  }
}
