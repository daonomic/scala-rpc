package scalether.domain.jackson

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonToken.VALUE_STRING
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer
import scalether.domain.Address

class AddressDeserializer extends StdScalarDeserializer[Address](classOf[Address]) {
  def deserialize(jp: JsonParser, ctxt: DeserializationContext): Address = jp.getCurrentToken match {
    case VALUE_STRING => Address(jp.getText.trim)
    case _ => ctxt.handleUnexpectedToken(_valueClass, jp).asInstanceOf[Address]
  }
}
