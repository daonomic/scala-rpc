package scalether.abi.tuple

import io.daonomic.rpc.domain.{Binary, Bytes}
import scalether.abi.{Decoded, Type}

object UnitType extends TupleType[Unit] {
  def string = "()"

  def types: List[Type[_]] = Nil

  def encode(t: Unit):Binary = Binary()

  def decode(data: Bytes, offset: Int) = Decoded((), offset)
}
