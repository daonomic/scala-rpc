package scalether.domain

import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.Gen
import org.scalatest.FlatSpec
import org.scalatest.prop.PropertyChecks

class AddressSpec extends FlatSpec with PropertyChecks {
  val address: Gen[Array[Byte]] = Gen.listOfN(20, arbitrary[Byte])
    .map(_.toArray)

  "Address" should "be equal if bytes are equals" in {
    forAll(address) { bytes =>
      assert(Address(bytes) == Address(bytes))
    }
  }

  it should "transform to checksum address" in {
    assert(Address("0x52908400098527886E0F7030069857D2E4169EE7").toChecksumString == "0x52908400098527886E0F7030069857D2E4169EE7")
    assert(Address("0x8617E340B3D01FA5F11F306F4090FD50E238070D").toChecksumString == "0x8617E340B3D01FA5F11F306F4090FD50E238070D")
    assert(Address("0xde709f2102306220921060314715629080e2fb77").toChecksumString == "0xde709f2102306220921060314715629080e2fb77")
    assert(Address("0x27b1fdb04752bbc536007a920d24acb045561c26").toChecksumString == "0x27b1fdb04752bbc536007a920d24acb045561c26")
    assert(Address("0x5aAeb6053F3E94C9b9A09f33669435E7Ef1BeAed").toChecksumString == "0x5aAeb6053F3E94C9b9A09f33669435E7Ef1BeAed")
    assert(Address("0xfB6916095ca1df60bB79Ce92cE3Ea74c37c5d359").toChecksumString == "0xfB6916095ca1df60bB79Ce92cE3Ea74c37c5d359")
  }
}
