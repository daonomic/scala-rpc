package scalether.abi

import java.math.BigInteger

import scalether.util.Padding.padLeft
import scalether.util.Bytes

class UintType(bits: Int) extends Type[BigInteger] {
  def string = s"uint$bits"

  def encode(t: BigInteger): Array[Byte] =
    padLeft(t.toByteArray, Bytes.ZERO)

  def decode(bytes: Array[Byte], offset: Int): Decoded[BigInteger] = {
    Decoded(new BigInteger(bytes.slice(offset, offset + 32)), offset + 32)
  }
}

//todo check size
object Uint8Type extends UintType(8)
object Uint16Type extends UintType(16)
object Uint24Type extends UintType(24)
object Uint32Type extends UintType(32)
object Uint40Type extends UintType(40)
object Uint48Type extends UintType(48)
object Uint56Type extends UintType(56)
object Uint64Type extends UintType(64)
object Uint72Type extends UintType(72)
object Uint80Type extends UintType(80)
object Uint88Type extends UintType(88)
object Uint96Type extends UintType(96)
object Uint104Type extends UintType(104)
object Uint112Type extends UintType(112)
object Uint120Type extends UintType(120)
object Uint128Type extends UintType(128)
object Uint136Type extends UintType(136)
object Uint144Type extends UintType(144)
object Uint152Type extends UintType(152)
object Uint160Type extends UintType(160)
object Uint168Type extends UintType(168)
object Uint176Type extends UintType(176)
object Uint184Type extends UintType(184)
object Uint192Type extends UintType(192)
object Uint200Type extends UintType(200)
object Uint208Type extends UintType(208)
object Uint216Type extends UintType(216)
object Uint224Type extends UintType(224)
object Uint232Type extends UintType(232)
object Uint240Type extends UintType(240)
object Uint248Type extends UintType(248)
object Uint256Type extends UintType(256)