package scalether.abi.array

import java.math.BigInteger

import scalether.abi.{Decoded, Type, Uint256Type}

import scala.collection.mutable.ListBuffer
import scala.reflect.ClassTag

abstract class ArrayType[T](`type`: Type[T])
                           (implicit classTag: ClassTag[T])
  extends Type[Array[T]] {

  def encode(value: Array[T]): Array[Byte] = {
    val head = ListBuffer[Byte]()
    val tail = ListBuffer[Byte]()
    val headSize = `type`.size.getOrElse(32) * value.length
    for (item <- value) {
      if (`type`.dynamic) {
        head ++= Uint256Type.encode(BigInteger.valueOf(headSize + tail.size))
        tail ++= `type`.encode(item)
      } else {
        head ++= `type`.encode(item)
      }
    }
    (head ++ tail).toArray
  }

  protected def decode(length: Int, bytes: Array[Byte], offset: Int): Decoded[Array[T]] = {
    if (`type`.dynamic) {
      var current = offset
      val list: IndexedSeq[T] = for (i <- 0 until length) yield {
        val bytesOffset = Uint256Type.decode(bytes, offset + i * 32).value.intValue()
        val decoded = `type`.decode(bytes, offset + bytesOffset)
        current = decoded.offset
        decoded.value
      }
      Decoded(list.toArray, current)
    } else {
      var current = offset
      val list: IndexedSeq[T] = for (_ <- 1 to length) yield {
        val decoded = `type`.decode(bytes, current)
        current = decoded.offset
        decoded.value
      }
      Decoded(list.toArray, current)
    }
  }
}
