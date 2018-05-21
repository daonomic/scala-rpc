package scalether.abi.tuple

import java.math.BigInteger

import io.daonomic.rpc.domain._
import scalether.abi.{Decoded, Type, Uint256Type}

import scala.collection.mutable.ListBuffer

class Tuple2Type[T1, T2](val type1: Type[T1], val type2: Type[T2]) extends TupleType[(T1, T2)] {
  def string = s"(${type1.string},${type2.string})"

  def types = List(type1, type2)

  def encode(value: (T1, T2)): Binary = {
    val head = ListBuffer[Byte]()
    val tail = ListBuffer[Byte]()
    if (type1.dynamic) {
      head ++= Uint256Type.encode(BigInteger.valueOf(headSize + tail.size)).bytes
      tail ++= type1.encode(value._1).bytes
    } else {
      head ++= type1.encode(value._1).bytes
    } 
    if (type2.dynamic) {
      head ++= Uint256Type.encode(BigInteger.valueOf(headSize + tail.size)).bytes
      tail ++= type2.encode(value._2).bytes
    } else {
      head ++= type2.encode(value._2).bytes
    } 
    Binary((head ++ tail).toArray)
  }

  def decode(data: Bytes, offset: Int): Decoded[(T1, T2)] = {
    val v1 = if (type1.dynamic) {
      val bytesOffset = Uint256Type.decode(data, offset + headOffset(0)).value.intValue()
      type1.decode(data, offset + bytesOffset)
    } else {
      type1.decode(data, offset + headOffset(0))
    } 
    val v2 = if (type2.dynamic) {
      val bytesOffset = Uint256Type.decode(data, offset + headOffset(1)).value.intValue()
      type2.decode(data, offset + bytesOffset)
    } else {
      type2.decode(data, offset + headOffset(1))
    } 
    Decoded((v1.value, v2.value), v2.offset)
  }
}

object Tuple2Type {
  def apply[T1, T2](type1: Type[T1], type2: Type[T2]): Tuple2Type[T1, T2] = 
    new Tuple2Type(type1, type2)
}
