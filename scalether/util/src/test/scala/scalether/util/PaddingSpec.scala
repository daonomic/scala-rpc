package scalether.util

import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.Gen
import org.scalatest.FlatSpec
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

class PaddingSpec extends FlatSpec with ScalaCheckPropertyChecks {
  private def bytesAndFill(size: Int): Gen[(Byte, Array[Byte])] = {
    val bytes = Gen.listOfN(size, arbitrary[Byte]).map(_.toArray)
    for (fill <- arbitrary[Byte]; bytes <- bytes) yield (fill, bytes)
  }

  val bytes16: Gen[(Byte, Array[Byte])] = bytesAndFill(16)
  val bytes48: Gen[(Byte, Array[Byte])] = bytesAndFill(48)

  "padRight" should "fill 16 right bytes" in {
    forAll(bytes16) { case (fill, bytes) =>
        assert(Padding.padRight(bytes, fill) sameElements (bytes ++ Bytes.filled(16, fill)))
    }
  }

  it should "work if length > 32" in {
    forAll(bytes48) { case (fill, bytes) =>
      assert(Padding.padRight(bytes, fill) sameElements (bytes ++ Bytes.filled(16, fill)))
    }
  }

  "padLeft" should "fill left 16 bytes" in {
    forAll(bytes16) { case (fill, bytes) =>
      assert(Padding.padLeft(bytes, fill) sameElements (Bytes.filled(16, fill) ++ bytes))
    }
  }

  it should "work if length > 32" in {
    forAll(bytes48) { case (fill, bytes) =>
      assert(Padding.padLeft(bytes, fill) sameElements (Bytes.filled(16, fill) ++ bytes))
    }
  }
}
