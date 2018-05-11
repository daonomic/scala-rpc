package scalether.abi

import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.Gen
import org.scalatest.FlatSpec
import org.scalatest.prop.PropertyChecks
import scalether.domain.Address
import scalether.util.{Bytes, Padding}

class AddressSpec extends FlatSpec with PropertyChecks {
  "Address" should "encode address" in {
    val address = "0x8283ffd0f535e1103c3599d2d00b85815774a896"
    val bytes = AddressType.encode(Address(address))
    assert("0x" + BigInt(bytes).toString(16) == address)
  }

  val address: Gen[List[Byte]] = Gen.listOfN(20, arbitrary[Byte])

  it should "decode encoded" in {
    forAll(address) { list =>
      val bytes = Padding.padLeft(list.toArray, Bytes.ZERO)
      val address = AddressType.decode(bytes, 0).value
      val encoded = AddressType.encode(address)
      assert(encoded sameElements bytes)
    }
  }
}
