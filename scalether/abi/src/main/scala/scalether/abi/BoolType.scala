package scalether.abi

import scalether.util.{Bytes, Padding}

object BoolType extends Type[Boolean] {
  val FALSE: Array[Byte] = Bytes.filled(32, Bytes.ZERO)
  val TRUE: Array[Byte] = Padding.padLeft(Array(Bytes.ONE), Bytes.ZERO)

  def string = "bool"

  def encode(value: Boolean): Array[Byte] = if (value) TRUE else FALSE

  def decode(bytes: Array[Byte], offset: Int): Decoded[Boolean] = Decoded(bytes(31) == 0x1, offset + 32)
}