package scalether.abi.tuple

import java.math.BigInteger

import io.daonomic.rpc.domain
import io.daonomic.rpc.domain.Binary
import scalether.abi.{Decoded, Type, Uint256Type}

import scala.collection.mutable.ListBuffer

class Tuple1Type[T1](val type1: Type[T1]) extends TupleType[T1] {
  def string = s"(${type1.string})"

  def types = List(type1)

  def encode(value: T1): Binary = {
    val head = ListBuffer[Byte]()
    val tail = ListBuffer[Byte]()
    if (type1.dynamic) {
      head ++= Uint256Type.encode(BigInteger.valueOf(headSize + tail.size)).bytes
      tail ++= type1.encode(value).bytes
    } else {
      head ++= type1.encode(value).bytes
    }
    Binary((head ++ tail).toArray)
  }

  def decode(data: domain.Bytes, offset: Int): Decoded[T1] = {
    if (type1.dynamic) {
      val bytesOffset = Uint256Type.decode(data, offset + headOffset(0)).value.intValue()
      type1.decode(data, offset + bytesOffset)
    } else {
      type1.decode(data, offset + headOffset(0))
    }
  }
}

object Tuple1Type {
  def apply[T1](type1: Type[T1]): Tuple1Type[T1] = new Tuple1Type(type1)
}
