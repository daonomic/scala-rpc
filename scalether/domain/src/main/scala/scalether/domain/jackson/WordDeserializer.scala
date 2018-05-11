package scalether.domain.jackson

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonToken.VALUE_STRING
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer
import scalether.domain.Word

class WordDeserializer extends StdScalarDeserializer[Word](classOf[Word]) {
  def deserialize(jp: JsonParser, ctxt: DeserializationContext): Word = jp.getCurrentToken match {
    case VALUE_STRING => Word(jp.getText.trim)
    case _ => ctxt.handleUnexpectedToken(_valueClass, jp).asInstanceOf[Word]
  }
}
