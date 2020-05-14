package io.daonomic.rpc.domain

import java.math.BigInteger
import java.util

import scalether.util.{Hex, Padding}

trait Bytes {
  def bytes: Array[Byte]

  def slice(from: Int, until: Int): Binary =
    Binary(bytes.slice(from, until))

  def add(other: Bytes): Binary = {
    Binary(bytes ++ other.bytes)
  }

  def add(other: Array[Byte]): Binary = {
    Binary(bytes ++ other)
  }

  def ++(other: Bytes): Binary = add(other)

  def ++(other: Array[Byte]): Binary = add(other)

  def length: Int = bytes.length

  def padded: Binary =
    Binary.apply(Padding.padLeft(bytes, scalether.util.Bytes.ZERO))

  def toBigInteger: BigInteger =
    new BigInteger(Hex.to(bytes), 16)

  override def toString: String =
    Hex.prefixed(bytes)

  def prefixed: String =
    toString()

  def hex: String =
    Hex.to(bytes)

  def toBinary: Binary

  override def equals(obj: scala.Any): Boolean = {
    if (!obj.isInstanceOf[Bytes]) {
      false
    } else {
      util.Arrays.equals(obj.asInstanceOf[Bytes].bytes, bytes)
    }
  }

  override def hashCode(): Int = bytes.toSeq.hashCode()
}
