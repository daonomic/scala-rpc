package scalether.abi.tuple

import java.math.BigInteger

import scalether.abi.{Decoded, Type, Uint256Type}

import scala.collection.mutable.ListBuffer

class Tuple16Type[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16](val type1: Type[T1], val type2: Type[T2], val type3: Type[T3], val type4: Type[T4], val type5: Type[T5], val type6: Type[T6], val type7: Type[T7], val type8: Type[T8], val type9: Type[T9], val type10: Type[T10], val type11: Type[T11], val type12: Type[T12], val type13: Type[T13], val type14: Type[T14], val type15: Type[T15], val type16: Type[T16]) extends TupleType[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16)] {
  def string = s"(${type1.string},${type2.string},${type3.string},${type4.string},${type5.string},${type6.string},${type7.string},${type8.string},${type9.string},${type10.string},${type11.string},${type12.string},${type13.string},${type14.string},${type15.string},${type16.string})"

  def types = List(type1, type2, type3, type4, type5, type6, type7, type8, type9, type10, type11, type12, type13, type14, type15, type16)

  def encode(value: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16)): Array[Byte] = {
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
    if (type11.dynamic) {
      head ++= Uint256Type.encode(BigInteger.valueOf(headSize + tail.size))
      tail ++= type11.encode(value._11)
    } else {
      head ++= type11.encode(value._11)
    } 
    if (type12.dynamic) {
      head ++= Uint256Type.encode(BigInteger.valueOf(headSize + tail.size))
      tail ++= type12.encode(value._12)
    } else {
      head ++= type12.encode(value._12)
    } 
    if (type13.dynamic) {
      head ++= Uint256Type.encode(BigInteger.valueOf(headSize + tail.size))
      tail ++= type13.encode(value._13)
    } else {
      head ++= type13.encode(value._13)
    } 
    if (type14.dynamic) {
      head ++= Uint256Type.encode(BigInteger.valueOf(headSize + tail.size))
      tail ++= type14.encode(value._14)
    } else {
      head ++= type14.encode(value._14)
    } 
    if (type15.dynamic) {
      head ++= Uint256Type.encode(BigInteger.valueOf(headSize + tail.size))
      tail ++= type15.encode(value._15)
    } else {
      head ++= type15.encode(value._15)
    } 
    if (type16.dynamic) {
      head ++= Uint256Type.encode(BigInteger.valueOf(headSize + tail.size))
      tail ++= type16.encode(value._16)
    } else {
      head ++= type16.encode(value._16)
    } 
    (head ++ tail).toArray
  }

  def decode(bytes: Array[Byte], offset: Int): Decoded[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16)] = {
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
    val v11 = if (type11.dynamic) {
      val bytesOffset = Uint256Type.decode(bytes, offset + headOffset(10)).value.intValue()
      type11.decode(bytes, offset + bytesOffset)
    } else {
      type11.decode(bytes, offset + headOffset(10))
    } 
    val v12 = if (type12.dynamic) {
      val bytesOffset = Uint256Type.decode(bytes, offset + headOffset(11)).value.intValue()
      type12.decode(bytes, offset + bytesOffset)
    } else {
      type12.decode(bytes, offset + headOffset(11))
    } 
    val v13 = if (type13.dynamic) {
      val bytesOffset = Uint256Type.decode(bytes, offset + headOffset(12)).value.intValue()
      type13.decode(bytes, offset + bytesOffset)
    } else {
      type13.decode(bytes, offset + headOffset(12))
    } 
    val v14 = if (type14.dynamic) {
      val bytesOffset = Uint256Type.decode(bytes, offset + headOffset(13)).value.intValue()
      type14.decode(bytes, offset + bytesOffset)
    } else {
      type14.decode(bytes, offset + headOffset(13))
    } 
    val v15 = if (type15.dynamic) {
      val bytesOffset = Uint256Type.decode(bytes, offset + headOffset(14)).value.intValue()
      type15.decode(bytes, offset + bytesOffset)
    } else {
      type15.decode(bytes, offset + headOffset(14))
    } 
    val v16 = if (type16.dynamic) {
      val bytesOffset = Uint256Type.decode(bytes, offset + headOffset(15)).value.intValue()
      type16.decode(bytes, offset + bytesOffset)
    } else {
      type16.decode(bytes, offset + headOffset(15))
    } 
    Decoded((v1.value, v2.value, v3.value, v4.value, v5.value, v6.value, v7.value, v8.value, v9.value, v10.value, v11.value, v12.value, v13.value, v14.value, v15.value, v16.value), v16.offset)
  }
}

object Tuple16Type {
  def apply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16](type1: Type[T1], type2: Type[T2], type3: Type[T3], type4: Type[T4], type5: Type[T5], type6: Type[T6], type7: Type[T7], type8: Type[T8], type9: Type[T9], type10: Type[T10], type11: Type[T11], type12: Type[T12], type13: Type[T13], type14: Type[T14], type15: Type[T15], type16: Type[T16]): Tuple16Type[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16] = 
    new Tuple16Type(type1, type2, type3, type4, type5, type6, type7, type8, type9, type10, type11, type12, type13, type14, type15, type16)
}
