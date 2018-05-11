package scalether.abi

import java.math.BigInteger

import scalether.util.{Bytes, Padding}

object BytesType extends Type[Array[Byte]] {
  def string = "bytes"

  override def size: Option[Int] = None

  def encode(bytes: Array[Byte]): Array[Byte] =
    Uint256Type.encode(BigInteger.valueOf(bytes.length)) ++ Padding.padRight(bytes, Bytes.ZERO)

  def decode(bytes: Array[Byte], offset: Int): Decoded[Array[Byte]] = {
    val ld = Uint256Type.decode(bytes, offset)
    val length = ld.value.intValue()
    Decoded(bytes.slice(ld.offset, ld.offset + length), ld.offset + Padding.getLength(length))
  }
}

class FixedBytesType(bits: Int) extends Type[Array[Byte]] {
  def string = s"bytes$bits"

  def encode(t: Array[Byte]): Array[Byte] =
    Padding.padRight(t.slice(0, bits), Bytes.ZERO)

  def decode(bytes: Array[Byte], offset: Int): Decoded[Array[Byte]] = {
    Decoded(bytes.slice(offset, offset + bits), offset + 32)
  }
}

object Bytes1Type extends FixedBytesType(1)
object Bytes2Type extends FixedBytesType(2)
object Bytes3Type extends FixedBytesType(3)
object Bytes4Type extends FixedBytesType(4)
object Bytes5Type extends FixedBytesType(5)
object Bytes6Type extends FixedBytesType(6)
object Bytes7Type extends FixedBytesType(7)
object Bytes8Type extends FixedBytesType(8)
object Bytes9Type extends FixedBytesType(9)
object Bytes10Type extends FixedBytesType(10)
object Bytes11Type extends FixedBytesType(11)
object Bytes12Type extends FixedBytesType(12)
object Bytes13Type extends FixedBytesType(13)
object Bytes14Type extends FixedBytesType(14)
object Bytes15Type extends FixedBytesType(15)
object Bytes16Type extends FixedBytesType(16)
object Bytes17Type extends FixedBytesType(17)
object Bytes18Type extends FixedBytesType(18)
object Bytes19Type extends FixedBytesType(19)
object Bytes20Type extends FixedBytesType(20)
object Bytes21Type extends FixedBytesType(21)
object Bytes22Type extends FixedBytesType(22)
object Bytes23Type extends FixedBytesType(23)
object Bytes24Type extends FixedBytesType(24)
object Bytes25Type extends FixedBytesType(25)
object Bytes26Type extends FixedBytesType(26)
object Bytes27Type extends FixedBytesType(27)
object Bytes28Type extends FixedBytesType(28)
object Bytes29Type extends FixedBytesType(29)
object Bytes30Type extends FixedBytesType(30)
object Bytes31Type extends FixedBytesType(31)
object Bytes32Type extends FixedBytesType(32)
