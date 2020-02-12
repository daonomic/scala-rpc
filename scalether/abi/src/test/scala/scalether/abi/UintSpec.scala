package scalether.abi

import java.math.BigInteger

import io.daonomic.rpc.domain.Binary
import org.scalatest.FlatSpec
import scalether.util.Hex

class UintSpec extends FlatSpec {
  "Uint64" should "encode values" in {
    assert(Uint64Type.encode(BigInteger.ZERO) == Binary(Hex.toBytes("%064d".format(0))))
    assert(Uint64Type.encode(BigInteger.valueOf(Long.MaxValue)) == Binary(Hex.toBytes(List.fill(48)('0').mkString + "7fffffffffffffff")))
  }

  "Uint256" should "decode values" in {
    assert(Uint64Type.decode(Binary.apply("0xbb8909fdcdbdc589ac13486a86ed32146c348d4ea6e87ffb6144195228e13dea"), 0).value
      == new BigInteger("84824629691196168496515066365354268540586911405614132411833823460169833594346"))
  }

  it should "encode values" in {
    assert(
      Uint256Type.encode(new BigInteger("84824629691196168496515066365354268540586911405614132411833823460169833594346"))
        == Binary.apply("0xbb8909fdcdbdc589ac13486a86ed32146c348d4ea6e87ffb6144195228e13dea")
    )
  }

}
