package scalether.domain

import java.math.BigInteger

import scalether.util.{Hex, Padding}

trait Bytes {
  def bytes: Array[Byte]

  def padded: Array[Byte] =
    Padding.padLeft(bytes, scalether.util.Bytes.ZERO)

  def toBigInteger: BigInteger =
    new BigInteger(Hex.to(bytes), 16)

  override def toString: String =
    Hex.prefixed(bytes)

  override def equals(obj: scala.Any): Boolean = {
    if (!obj.isInstanceOf[Bytes]) {
      false
    } else {
      obj.asInstanceOf[Bytes].bytes.deep == bytes.deep
    }
  }

  override def hashCode(): Int = bytes.toSeq.hashCode()
}
