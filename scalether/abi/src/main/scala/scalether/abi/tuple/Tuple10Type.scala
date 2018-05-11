package scalether.abi.tuple

import java.math.BigInteger

import scalether.abi.{Decoded, Type, Uint256Type}

import scala.collection.mutable.ListBuffer

class Tuple10Type[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10](val type1: Type[T1], val type2: Type[T2], val type3: Type[T3], val type4: Type[T4], val type5: Type[T5], val type6: Type[T6], val type7: Type[T7], val type8: Type[T8], val type9: Type[T9], val type10: Type[T10]) extends TupleType[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10)] {
  def string = s"(${type1.string},${type2.string},${type3.string},${type4.string},${type5.string},${type6.string},${type7.string},${type8.string},${type9.string},${type10.string})"

  def types = List(type1, type2, type3, type4, type5, type6, type7, type8, type9, type10)

  def encode(value: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10)): Array[Byte] = {
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
    if (type4.dynamic) {
      head ++= Uint256Type.encode(BigInteger.valueOf(headSize + tail.size))
      tail ++= type4.encode(value._4)
    } else {
      head ++= type4.encode(value._4)
    } 
    if (type5.dynamic) {
      head ++= Uint256Type.encode(BigInteger.valueOf(headSize + tail.size))
      tail ++= type5.encode(value._5)
    } else {
      head ++= type5.encode(value._5)
    } 
    if (type6.dynamic) {
      head ++= Uint256Type.encode(BigInteger.valueOf(headSize + tail.size))
      tail ++= type6.encode(value._6)
    } else {
      head ++= type6.encode(value._6)
    } 
    if (type7.dynamic) {
      head ++= Uint256Type.encode(BigInteger.valueOf(headSize + tail.size))
      tail ++= type7.encode(value._7)
    } else {
      head ++= type7.encode(value._7)
    } 
    if (type8.dynamic) {
      head ++= Uint256Type.encode(BigInteger.valueOf(headSize + tail.size))
      tail ++= type8.encode(value._8)
    } else {
      head ++= type8.encode(value._8)
    } 
    if (type9.dynamic) {
      head ++= Uint256Type.encode(BigInteger.valueOf(headSize + tail.size))
      tail ++= type9.encode(value._9)
    } else {
      head ++= type9.encode(value._9)
    } 
    if (type10.dynamic) {
      head ++= Uint256Type.encode(BigInteger.valueOf(headSize + tail.size))
      tail ++= type10.encode(value._10)
    } else {
      head ++= type10.encode(value._10)
    } 
    (head ++ tail).toArray
  }

  def decode(bytes: Array[Byte], offset: Int): Decoded[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10)] = {
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
    val v4 = if (type4.dynamic) {
      val bytesOffset = Uint256Type.decode(bytes, offset + headOffset(3)).value.intValue()
      type4.decode(bytes, offset + bytesOffset)
    } else {
      type4.decode(bytes, offset + headOffset(3))
    } 
    val v5 = if (type5.dynamic) {
      val bytesOffset = Uint256Type.decode(bytes, offset + headOffset(4)).value.intValue()
      type5.decode(bytes, offset + bytesOffset)
    } else {
      type5.decode(bytes, offset + headOffset(4))
    } 
    val v6 = if (type6.dynamic) {
      val bytesOffset = Uint256Type.decode(bytes, offset + headOffset(5)).value.intValue()
      type6.decode(bytes, offset + bytesOffset)
    } else {
      type6.decode(bytes, offset + headOffset(5))
    } 
    val v7 = if (type7.dynamic) {
      val bytesOffset = Uint256Type.decode(bytes, offset + headOffset(6)).value.intValue()
      type7.decode(bytes, offset + bytesOffset)
    } else {
      type7.decode(bytes, offset + headOffset(6))
    } 
    val v8 = if (type8.dynamic) {
      val bytesOffset = Uint256Type.decode(bytes, offset + headOffset(7)).value.intValue()
      type8.decode(bytes, offset + bytesOffset)
    } else {
      type8.decode(bytes, offset + headOffset(7))
    } 
    val v9 = if (type9.dynamic) {
      val bytesOffset = Uint256Type.decode(bytes, offset + headOffset(8)).value.intValue()
      type9.decode(bytes, offset + bytesOffset)
    } else {
      type9.decode(bytes, offset + headOffset(8))
    } 
    val v10 = if (type10.dynamic) {
      val bytesOffset = Uint256Type.decode(bytes, offset + headOffset(9)).value.intValue()
      type10.decode(bytes, offset + bytesOffset)
    } else {
      type10.decode(bytes, offset + headOffset(9))
    } 
    Decoded((v1.value, v2.value, v3.value, v4.value, v5.value, v6.value, v7.value, v8.value, v9.value, v10.value), v10.offset)
  }
}

object Tuple10Type {
  def apply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10](type1: Type[T1], type2: Type[T2], type3: Type[T3], type4: Type[T4], type5: Type[T5], type6: Type[T6], type7: Type[T7], type8: Type[T8], type9: Type[T9], type10: Type[T10]): Tuple10Type[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10] = 
    new Tuple10Type(type1, type2, type3, type4, type5, type6, type7, type8, type9, type10)
}
