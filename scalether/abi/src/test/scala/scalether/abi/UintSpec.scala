package scalether.abi

import java.math.BigInteger

import org.scalatest.FlatSpec
import scalether.util.Hex

class UintSpec extends FlatSpec {
  "Uint64" should "encode values" in {
    assert(Uint64Type.encode(BigInteger.ZERO) sameElements Hex.toBytes("%064d".format(0)))
    assert(Uint64Type.encode(BigInteger.valueOf(Long.MaxValue)) sameElements Hex.toBytes(List.fill(48)('0').mkString + "7fffffffffffffff"))
  }
}
