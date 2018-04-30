package scalether.abi

import org.scalatest.FlatSpec
import org.scalatest.prop.PropertyChecks

class BoolSpec extends FlatSpec with PropertyChecks {
  "BoolType" should "encode decoded value" in {
    forAll { bool: Boolean =>
      val encoded = BoolType.encode(bool)
      val decoded = BoolType.decode(encoded, 0)
      assert(decoded.value == bool)
      assert(decoded.offset == encoded.length)
    }
  }
}
