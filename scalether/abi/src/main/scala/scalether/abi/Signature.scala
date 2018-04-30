package scalether.abi

import java.nio.charset.StandardCharsets

import scalether.abi.tuple.TupleType
import scalether.util.{Hash, Hex}

case class Signature[I, O](name: String, in: TupleType[I], out: TupleType[O]) {
  def id: Array[Byte] = {
    val bytes = toString.getBytes(StandardCharsets.US_ASCII)
    Hash.sha3(bytes).slice(0, 4)
  }

  def encode(in: I): Array[Byte] =
    id ++ this.in.encode(in)

  def decode(out: Array[Byte]): O =
    this.out.decode(out, 0).value

  override def toString: String = name + in.string
}
