package scalether.abi.tuple

import scalether.abi.Decoded

object UnitType extends TupleType[Unit] {
  def string = "()"

  def types = Nil

  def encode(t: Unit) = Array()

  def decode(bytes: Array[Byte], offset: Int) = Decoded(Unit, offset)
}
