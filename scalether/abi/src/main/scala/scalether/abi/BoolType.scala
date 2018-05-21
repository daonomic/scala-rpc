package scalether.abi

import io.daonomic.rpc.domain
import io.daonomic.rpc.domain.Binary
import scalether.util.{Bytes, Padding}

object BoolType extends Type[Boolean] {
  val FALSE: Array[Byte] = Bytes.filled(32, Bytes.ZERO)
  val TRUE: Array[Byte] = Padding.padLeft(Array(Bytes.ONE), Bytes.ZERO)

  def string = "bool"

  def encode(value: Boolean): Binary = if (value) Binary(TRUE) else Binary(FALSE)

  def decode(data: domain.Bytes, offset: Int): Decoded[Boolean] = Decoded(data.bytes(31) == 0x1, offset + 32)
}