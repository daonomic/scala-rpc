package scalether.test

import java.math.BigInteger

import cats.implicits._
import org.scalacheck.Gen
import org.scalatest.FlatSpec
import org.scalatest.prop.PropertyChecks
import scalether.abi.array.VarArrayType
import scalether.abi.tuple.Tuple2Type
import scalether.abi.{StringType, Uint256Type}
import scalether.domain.Address
import scalether.domain.response.Log
import scalether.util.Hex

import scala.util.Try

class AbiTupleIntegrationSpec extends FlatSpec with PropertyChecks with IntegrationSpec {

  val test: IntegrationTest[Try] = IntegrationTest.deployAndWait(sender, poller).get

  val addressAndValue: Gen[(Address, BigInteger)] = for {
    address <- Generators.address
    value <- Gen.chooseNum[Long](0, 100000)
  } yield (address, BigInteger.valueOf(value))

  val stringAndValue: Gen[(String, BigInteger)] = for {
    string <- Gen.alphaNumStr
    value <- Gen.chooseNum[Long](0, 100000)
  } yield (string, BigInteger.valueOf(value))

  val list1: Gen[List[(Address, BigInteger)]] = Gen.listOf(addressAndValue)
  val list2: Gen[List[(String, BigInteger)]] = Gen.listOf(stringAndValue)

  "AbiEncoder" should "encode array of tuples" in {
    forAll(list1) {
      testValues =>
        val hash = test.setRates(testValues.toArray).execute().get
        val receipt = ethereum.ethGetTransactionReceipt(hash).get
        assert(receipt.isDefined)
        assert(receipt.get.success)
        assert(testValues == logs1ToList(receipt.get.logs))
    }
  }

  it should "encode" in {
    println(Hex.prefixed(Tuple2Type(StringType, Uint256Type).encode("", BigInteger.ZERO)))
  }

  it should "encode array" in {
    println(Hex.prefixed(VarArrayType(Tuple2Type(StringType, Uint256Type)).encode(Array(("", BigInteger.ZERO)))))
  }

  it should "decode struct with string" in {
    val result = test.getStructWithString.get
    assert(result == ("", BigInteger.ZERO))
  }

  it should "decode arrays with structs with string" in {
    val result = test.getStructsWithString.get
    assert(result.toList == List(("", BigInteger.ZERO)))
  }

  it should "encode array of tuples with strings" in {
    forAll(list2) {
      testValues =>
        val hash = test.checkStructsWithString(testValues.toArray).execute().get
        val receipt = ethereum.ethGetTransactionReceipt(hash).get
        assert(receipt.isDefined)
        assert(receipt.get.success)
        assert(testValues == logs2ToList(receipt.get.logs))
    }
  }

  it should "encode single struct" in {
    forAll(addressAndValue) {
      case (token, value) =>
        val hash = test.setRate(token, value).execute().get
        val receipt = ethereum.ethGetTransactionReceipt(hash).get
        assert(receipt.isDefined)
        assert(receipt.get.success)
        val rates = logs1ToList(receipt.get.logs)
        assert(rates.size == 1)
        assert(rates.head == (token, value))
    }
  }

  it should "encode and decode struct" in {
    forAll(addressAndValue) {
      case (token, value) =>
        test.setRate(token, value).execute().get
        val result = test.getRate.call().get
        assert(result == (token, value))
    }
  }

  private def logs1ToList(logs: List[Log]): List[(Address, BigInteger)] = {
    logs
      .map(log => RateEvent(log))
      .map(ev => (ev.token, ev.value))
  }

  private def logs2ToList(logs: List[Log]): List[(String, BigInteger)] = {
    logs
      .map(log => StringEvent(log))
      .map(ev => (ev.str, ev.value))
  }

}
