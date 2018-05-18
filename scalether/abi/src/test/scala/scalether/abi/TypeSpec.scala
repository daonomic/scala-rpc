package scalether.abi

import org.scalacheck.Gen
import org.scalatest.FlatSpec
import scalether.abi.array.{FixArrayType, VarArrayType}
import scalether.abi.tuple.TupleType

class TypeSpec extends FlatSpec {
  "Type" should "recognize simple types" in {
    assert(Type("string") == StringType)
    assert(Type("bool") == BoolType)
    assert(Type("address") == AddressType)
    assert(Type("bytes") == BytesType)
  }

  val bits: Gen[Short] = Gen.choose[Short](1, 32)

  it should "recognize uint types" in {
    for(i <- 1 to 32) {
      assert(Type("uint" + (i * 8)) == UintType((i * 8).toShort))
    }
  }

  it should "recognize int types" in {
    for(i <- 1 to 32) {
      assert(Type("int" + (i * 8)) == IntType((i * 8).toShort))
    }
  }

  it should "recognize bytes types" in {
    for(i <- 1 to 32) {
      assert(Type("bytes" + (i * 8)) == FixedBytesType((i * 8).toShort))
    }
  }

  it should "recognize var array types" in {
    val result = Type("uint[]")
    assert(result.isInstanceOf[VarArrayType[_]])
    assert(result.dynamic)
    assert(result.asInstanceOf[VarArrayType[_]].`type` == Uint256Type)
    assert(result.string == "uint256[]")
  }

  it should "recognize fixed array types" in {
    val result = Type("uint[5]")
    assert(result.isInstanceOf[FixArrayType[_]])
    assert(!result.dynamic)
    assert(result.asInstanceOf[FixArrayType[_]].`type` == Uint256Type)
    assert(result.asInstanceOf[FixArrayType[_]].length == 5)
    assert(result.string == "uint256[5]")
  }

  it should "recognize tuples" in {
    for(i <- 0 to 22) {
      val types = for(_ <- 1 to i) yield "bool"
      val result = Type(s"(${types.mkString(",")})")
      assert(result.isInstanceOf[TupleType[_]])
      assert(result.asInstanceOf[TupleType[_]].types.size == i)
      for(j <- 0 until i) {
        assert(result.asInstanceOf[TupleType[_]].types(j) == BoolType)
      }
    }
  }
}
