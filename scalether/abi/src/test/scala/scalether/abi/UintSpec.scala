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
}
