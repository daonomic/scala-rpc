package scalether.abi

import java.nio.charset.StandardCharsets

import io.daonomic.rpc.domain
import io.daonomic.rpc.domain.Binary
import scalether.abi.tuple.TupleType
import scalether.util.Hash

case class Signature[I, O](name: String, in: TupleType[I], out: TupleType[O]) {
  def id: Binary = {
    val bytes = toString.getBytes(StandardCharsets.US_ASCII)
    Binary(Hash.sha3(bytes)).slice(0, 4)
  }

  def encode(in: I): Binary =
    id ++ this.in.encode(in)

  def decode(out: domain.Bytes): O =
    this.out.decode(out, 0).value

  override def toString: String = name + in.string
}
