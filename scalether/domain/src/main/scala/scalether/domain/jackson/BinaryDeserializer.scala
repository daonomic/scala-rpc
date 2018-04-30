package scalether.domain.jackson

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonToken.VALUE_STRING
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer
import scalether.domain.Binary

class BinaryDeserializer extends StdScalarDeserializer[Binary](classOf[Binary]) {
  def deserialize(jp: JsonParser, ctxt: DeserializationContext): Binary = jp.getCurrentToken match {
    case VALUE_STRING => Binary(jp.getText.trim)
    case _ => ctxt.handleUnexpectedToken(_valueClass, jp).asInstanceOf[Binary]
  }
}
