package scalether.core.json

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer
import scalether.domain.request.{OrTopicFilter, SimpleTopicFilter, TopicFilter}

class TopicFilterSerializer extends StdScalarSerializer[TopicFilter](classOf[TopicFilter]) {
  override def serialize(value: TopicFilter, gen: JsonGenerator, provider: SerializerProvider): Unit = {
    value match {
      case SimpleTopicFilter(word) => gen.writeString(word.toString)
      case OrTopicFilter(words) =>
        gen.writeStartArray()
        for(word <- words) {
          gen.writeString(word.toString)
        }
        gen.writeEndArray()
    }
  }
}
