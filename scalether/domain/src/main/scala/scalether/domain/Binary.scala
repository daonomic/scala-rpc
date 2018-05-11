package scalether.domain

import com.fasterxml.jackson.databind.annotation.{JsonDeserialize, JsonSerialize}
import scalether.domain.jackson.{BinaryDeserializer, BinarySerializer}
import scalether.util.Hex

@JsonSerialize(using = classOf[BinarySerializer])
@JsonDeserialize(using = classOf[BinaryDeserializer])
case class Binary(bytes: Array[Byte]) extends Bytes

object Binary {
  def apply(): Binary = new Binary(Array())

  def apply(bytes: Array[Byte]): Binary =
    if (bytes == null) new Binary(Array()) else new Binary(bytes)

  def apply(hex: String): Binary =
    if (hex == null) new Binary(Array()) else new Binary(Hex.toBytes(hex))
}
