package scalether.domain

import com.fasterxml.jackson.databind.annotation.{JsonDeserialize, JsonSerialize}
import scalether.domain.jackson.{WordDeserializer, WordSerializer}
import scalether.util.Hex

@JsonSerialize(using = classOf[WordSerializer])
@JsonDeserialize(using = classOf[WordDeserializer])
case class Word(bytes: Array[Byte]) extends Bytes {
  assert(bytes.length == 32)

  def toBinary: Binary = Binary(bytes)
}

object Word {
  def apply(hex: String): Word =
    Word(Hex.toBytes(hex))

  def apply(binary: Binary): Word =
    Word(binary.bytes)
}
