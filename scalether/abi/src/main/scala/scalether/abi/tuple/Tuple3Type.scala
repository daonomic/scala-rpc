package scalether.abi.tuple

import java.math.BigInteger

import scalether.abi.{Decoded, Type, Uint256Type}

import scala.collection.mutable.ListBuffer

class Tuple3Type[T1, T2, T3](val type1: Type[T1], val type2: Type[T2], val type3: Type[T3]) extends TupleType[(T1, T2, T3)] {
  def string = s"(${type1.string},${type2.string},${type3.string})"

  def types = List(type1, type2, type3)

  def encode(value: (T1, T2, T3)): Array[Byte] = {
    val head = ListBuffer[Byte]()
    val tail = ListBuffer[Byte]()
    if (type1.dynamic) {
      head ++= Uint256Type.encode(BigInteger.valueOf(headSize + tail.size))
      tail ++= type1.encode(value._1)
    } else {
      head ++= type1.encode(value._1)
    } 
    if (type2.dynamic) {
      head ++= Uint256Type.encode(BigInteger.valueOf(headSize + tail.size))
      tail ++= type2.encode(value._2)
    } else {
      head ++= type2.encode(value._2)
    } 
    if (type3.dynamic) {
      head ++= Uint256Type.encode(BigInteger.valueOf(headSize + tail.size))
      tail ++= type3.encode(value._3)
    } else {
      head ++= type3.encode(value._3)
    } 
    (head ++ tail).toArray
  }

  def decode(bytes: Array[Byte], offset: Int): Decoded[(T1, T2, T3)] = {
    val v1 = if (type1.dynamic) {
      val bytesOffset = Uint256Type.decode(bytes, offset + headOffset(0)).value.intValue()
      type1.decode(bytes, offset + bytesOffset)
    } else {
      type1.decode(bytes, offset + headOffset(0))
    } 
    val v2 = if (type2.dynamic) {
      val bytesOffset = Uint256Type.decode(bytes, offset + headOffset(1)).value.intValue()
      type2.decode(bytes, offset + bytesOffset)
    } else {
      type2.decode(bytes, offset + headOffset(1))
    } 
    val v3 = if (type3.dynamic) {
      val bytesOffset = Uint256Type.decode(bytes, offset + headOffset(2)).value.intValue()
      type3.decode(bytes, offset + bytesOffset)
    } else {
      type3.decode(bytes, offset + headOffset(2))
    } 
    Decoded((v1.value, v2.value, v3.value), v3.offset)
  }
}

object Tuple3Type {
  def apply[T1, T2, T3](type1: Type[T1], type2: Type[T2], type3: Type[T3]): Tuple3Type[T1, T2, T3] = 
    new Tuple3Type(type1, type2, type3)
}
