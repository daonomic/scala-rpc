package scalether.generator.domain

import com.fasterxml.jackson.databind.ObjectMapper
import org.scalatest.FlatSpec
import scalether.generator.ContractGenerator

import scala.language.postfixOps

class AbiItemSpec extends FlatSpec {
  val converter: ObjectMapper = ContractGenerator.createMapper()

  "AbiItem" should "be deserialized correctly" in {
    val items = converter.readValue("[{\n\"type\":\"event\",\n\"inputs\": [{\"name\":\"a\",\"type\":\"uint256\",\"indexed\":true},{\"name\":\"b\",\"type\":\"bytes32\",\"indexed\":false}],\n\"name\":\"Event\"\n}, {\n\"type\":\"event\",\n\"inputs\": [{\"name\":\"a\",\"type\":\"uint256\",\"indexed\":true},{\"name\":\"b\",\"type\":\"bytes32\",\"indexed\":false}],\n\"name\":\"Event2\"\n}, {\n\"type\":\"constructor\",\n\"inputs\": [{\"name\":\"a\",\"type\":\"uint256\"}],\n\"name\":\"foo\",\n\"outputs\": []\n}, {\n\"inputs\": [{\"name\":\"a\",\"type\":\"uint256\"}],\n\"name\":\"foo\",\n\"outputs\": []\n}]", classOf[Array[AbiItem]])
    assert(items.length == 4)
    assert(items.head.isInstanceOf[AbiEvent])
    assert(items.last.isInstanceOf[AbiFunction])
    assert(items(2).asInstanceOf[AbiFunction].getType == AbiFunctionType.CONSTRUCTOR)
    assert(items(3).asInstanceOf[AbiFunction].getType == AbiFunctionType.FUNCTION)
  }
}
