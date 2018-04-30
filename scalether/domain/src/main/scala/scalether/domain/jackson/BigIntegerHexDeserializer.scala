package scalether.domain.jackson

import java.math.BigInteger

import com.fasterxml.jackson.core.JsonToken.{START_ARRAY, VALUE_NUMBER_FLOAT, VALUE_NUMBER_INT, VALUE_STRING}
import com.fasterxml.jackson.core.{JsonParser, JsonToken}
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer
import scalether.domain.implicits

object BigIntegerHexDeserializer extends StdScalarDeserializer[BigInteger](classOf[BigInteger]) {
  override def deserialize(jp: JsonParser, ctxt: DeserializationContext): BigInteger = {
    val t = jp.getCurrentToken
    t match {
      case VALUE_NUMBER_INT | VALUE_NUMBER_FLOAT => new BigInteger(jp.getText.trim)
      case VALUE_STRING =>
        val text = jp.getText.trim
        try {
          implicits.stringToBigInteger(text)
        } catch {
          case _: IllegalArgumentException => throw ctxt.weirdStringException(text, _valueClass, "not a valid representation")
        }
      case START_ARRAY if ctxt.isEnabled(UNWRAP_SINGLE_VALUE_ARRAYS) =>
        jp.nextToken()
        val value = deserialize(jp, ctxt)
        if (jp.nextToken() != JsonToken.END_ARRAY) {
          throw ctxt.wrongTokenException(jp, JsonToken.END_ARRAY, "Attempted to unwrap array for single value but there was more than a single value in the array")
        }
        value
      case _ =>
        ctxt.handleUnexpectedToken(_valueClass, jp).asInstanceOf[BigInteger]
    }
  }
}
