package io.daonomic.rpc.domain

object BinaryFactory {
  def createByteArray(size: Int): Array[Byte] =
    Array.fill(size)((scala.util.Random.nextInt(256) - 128).toByte)

  def create(size: Int): Binary =
    Binary.apply(createByteArray(size))
}
