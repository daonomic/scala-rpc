package scalether.abi

import java.math.BigInteger

import org.scalatest.FlatSpec
import org.scalatest.prop.PropertyChecks
import scalether.util.Bytes

class StringSpec extends FlatSpec with PropertyChecks {
  "StringType" should "decode encoded" in {
    forAll { s: String =>
      val encoded = StringType.encode(s)
      val decoded = StringType.decode(encoded, 0)

      assert(decoded.value == s)
      assert(decoded.offset == encoded.length)
    }
  }

  it should "encode empty string" in {
    assert(StringType.encode("") sameElements Uint256Type.encode(BigInteger.ZERO))
  }

  it should "encode one-element string" in {
    val c = 'a'
    val bytes = StringType.encode(c + "")
    val test = Uint256Type.encode(BigInteger.valueOf(1)) ++ Array(c.toByte) ++ Bytes.filled(31, Bytes.ZERO)

    assert(bytes sameElements test)
  }
}
