package scalether.abi

import java.math.BigInteger

import org.scalatest.FlatSpec
import org.scalatest.mockito.MockitoSugar
import scalether.abi.array.VarArrayType
import scalether.abi.tuple.{Tuple1Type, Tuple4Type, UnitType}
import scalether.util.Hex

class SignatureSpec extends FlatSpec with MockitoSugar{
  val totalSupply = Signature("totalSupply", UnitType, Tuple1Type(Uint256Type))

  "Signature" should "encode totalSupply() returns (uint256)" in {
    assert(Hex.prefixed(totalSupply.encode()) == "0x18160ddd")
  }

  it should "encode example from ethereum docs" in {
    val signature = Signature("f", new Tuple4Type(Uint256Type, new VarArrayType(Uint32Type), Bytes10Type, BytesType), UnitType)
    val result = signature.encode((BigInteger.valueOf(0x123), Array(BigInteger.valueOf(0x456), BigInteger.valueOf(0x789)), "1234567890".getBytes(), "Hello, world!".getBytes()))
    val testResult = "0x8be65246" + "0000000000000000000000000000000000000000000000000000000000000123" + "0000000000000000000000000000000000000000000000000000000000000080" + "3132333435363738393000000000000000000000000000000000000000000000" + "00000000000000000000000000000000000000000000000000000000000000e0" + "0000000000000000000000000000000000000000000000000000000000000002" + "0000000000000000000000000000000000000000000000000000000000000456" + "0000000000000000000000000000000000000000000000000000000000000789" + "000000000000000000000000000000000000000000000000000000000000000d" + "48656c6c6f2c20776f726c642100000000000000000000000000000000000000"
    assert(Hex.prefixed(result) == testResult)
  }
}
