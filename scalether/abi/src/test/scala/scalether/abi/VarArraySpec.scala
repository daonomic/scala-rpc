package scalether.abi

import java.math.BigInteger

import org.scalatest.FlatSpec
import scalether.abi.AbiTestConst._
import scalether.abi.array.VarArrayType

class VarArraySpec extends FlatSpec {
  val arr = new VarArrayType(Uint256Type)

  "VarArrayType" should "decode empty array" in {
    val result = arr.decode(zero, 0)
    assert(result.offset == 32)
    assert(result.value.isEmpty)
  }

  it should "decode non-empty uint256" in {
    val result = arr.decode(Uint256Type.encode(BigInteger.valueOf(2)) ++ ten ++ maxLong, 0)
    assert(result.offset == 96)
    assert(result.value sameElements Array(BigInteger.valueOf(10), BigInteger.valueOf(Long.MaxValue)))
  }

  it should "encode empty array" in {
    val result = arr.encode(Array())
    assert(result sameElements zero)
  }

  it should "encode non-empty array" in {
    val result = arr.encode(Array(BigInteger.ZERO))
    assert(result sameElements (one ++ zero))
  }
}
