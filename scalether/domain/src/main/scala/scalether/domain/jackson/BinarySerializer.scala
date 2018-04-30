package scalether.domain.jackson

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer
import scalether.domain.Binary
import scalether.util.Hex

class BinarySerializer extends StdScalarSerializer[Binary](classOf[Binary]) {
  def serialize(value: Binary, gen: JsonGenerator, provider: SerializerProvider): Unit =
    gen.writeString(Hex.prefixed(value.bytes))
}
