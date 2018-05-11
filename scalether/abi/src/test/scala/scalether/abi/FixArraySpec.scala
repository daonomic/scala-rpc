package scalether.abi

import java.math.BigInteger

import org.scalatest.FlatSpec
import scalether.abi.AbiTestConst._
import scalether.abi.array.FixArrayType

class FixArraySpec extends FlatSpec {
  val arr1 = new FixArrayType(1, Uint256Type)
  val arr2 = new FixArrayType(2, Uint256Type)

  "FixArrayType" should "decode 1-item array" in {
    val result = arr1.decode(one, 0)
    assert(result.offset == 32)
    assert(result.value sameElements Array(BigInteger.valueOf(1)))
  }

  it should "decode arrays with greater lengths" in {
    val result = arr2.decode(ten ++ one, 0)
    assert(result.offset == 64)
    assert(result.value sameElements Array(BigInteger.valueOf(10), BigInteger.valueOf(1)))
  }

  it should "encode arrays" in {
    val result = arr2.encode(Array(BigInteger.valueOf(Long.MaxValue), BigInteger.valueOf(0)))
    assert(result sameElements (maxLong ++ zero))
  }
}
