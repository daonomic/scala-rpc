package io.daonomic.rpc.domain

import com.fasterxml.jackson.databind.annotation.{JsonDeserialize, JsonSerialize}
import io.daonomic.rpc.domain.jackson.{BinaryDeserializer, BinarySerializer}
import scalether.util.Hex

@JsonSerialize(using = classOf[BinarySerializer])
@JsonDeserialize(using = classOf[BinaryDeserializer])
case class Binary(bytes: Array[Byte]) extends Bytes {
  override def toBinary: Binary = this
}

object Binary {
  val empty: Binary = new Binary(Array())

  def apply(): Binary = empty

  def apply(bytes: Array[Byte]): Binary =
    if (bytes == null) new Binary(Array()) else new Binary(bytes)

  def apply(hex: String): Binary =
    if (hex == null) new Binary(Array()) else new Binary(Hex.toBytes(hex))
}
