package scalether.abi

import java.math.BigInteger

import org.scalacheck.Gen
import org.scalatest.FlatSpec
import org.scalatest.prop.PropertyChecks
import scalether.abi.array.{FixArrayType, VarArrayType}
import scalether.abi.tuple.Tuple2Type

class Tuple2Spec extends FlatSpec with PropertyChecks {
  val tuple2Type = Tuple2Type(new FixArrayType(1, Uint256Type), new VarArrayType(Uint256Type))

  val pos: Gen[BigInteger] = Gen.posNum[Int].map(i => BigInteger.valueOf(i))
  val array: Gen[Array[BigInteger]] = Gen.listOf(pos).map(_.toArray)

  "Tuple2Type" should "encode var and fix arrays" in {
    forAll(for (i1 <- pos; i2 <- pos) yield (i1, i2)) { case (fixVal: BigInteger, varVal: BigInteger) =>
      val fixArray = Array(fixVal)
      val varArray = Array(varVal)

      val encoded = tuple2Type.encode((fixArray, varArray))
      val test = Uint256Type.encode(fixVal) ++
        Uint256Type.encode(BigInteger.valueOf(64)) ++
        Uint256Type.encode(BigInteger.valueOf(1)) ++
        Uint256Type.encode(varVal)
      assert(encoded sameElements test)
    }
  }

  val test: Gen[(BigInteger, Array[BigInteger], Array[BigInteger])] = for (i <- pos; l1 <- array; l2 <- array) yield (i, l1, l2)

  it should "encode decoded" in {
    forAll(test) { case (start1: BigInteger, list1: Array[BigInteger], list2: Array[BigInteger]) =>
      val fixArray = Array(start1) ++ list1
      val varArray = list2
      val typ = Tuple2Type(new FixArrayType(fixArray.length, Uint256Type), new VarArrayType(Uint256Type))

      val encoded = typ.encode((fixArray, varArray))
      val decoded = typ.decode(encoded, 0)
      val (decodedFix, decodedVar) = decoded.value
      assert(decoded.offset == encoded.length)
      assert(decodedFix sameElements fixArray)
      assert(decodedVar sameElements varArray)
    }
  }
}
