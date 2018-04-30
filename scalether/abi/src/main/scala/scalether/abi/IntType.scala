package scalether.abi

import java.math.BigInteger

import scalether.util.Padding.padLeft
import scalether.util.Bytes

class IntType(bits: Int) extends Type[BigInteger] {
  def string = s"int$bits"

  def encode(t: BigInteger): Array[Byte] =
    padLeft(t.toByteArray, if (t.compareTo(BigInteger.ZERO) >= 0) Bytes.ZERO else Bytes.ONE)

  def decode(bytes: Array[Byte], offset: Int): Decoded[BigInteger] =
    Decoded(new BigInteger(bytes.slice(offset, offset + 32)), offset + 32)
}

object Int8Type extends IntType(8)
object Int16Type extends IntType(16)
object Int24Type extends IntType(24)
object Int32Type extends IntType(32)
object Int40Type extends IntType(40)
object Int48Type extends IntType(48)
object Int56Type extends IntType(56)
object Int64Type extends IntType(64)
object Int72Type extends IntType(72)
object Int80Type extends IntType(80)
object Int88Type extends IntType(88)
object Int96Type extends IntType(96)
object Int104Type extends IntType(104)
object Int112Type extends IntType(112)
object Int120Type extends IntType(120)
object Int128Type extends IntType(128)
object Int136Type extends IntType(136)
object Int144Type extends IntType(144)
object Int152Type extends IntType(152)
object Int160Type extends IntType(160)
object Int168Type extends IntType(168)
object Int176Type extends IntType(176)
object Int184Type extends IntType(184)
object Int192Type extends IntType(192)
object Int200Type extends IntType(200)
object Int208Type extends IntType(208)
object Int216Type extends IntType(216)
object Int224Type extends IntType(224)
object Int232Type extends IntType(232)
object Int240Type extends IntType(240)
object Int248Type extends IntType(248)
object Int256Type extends IntType(256)
