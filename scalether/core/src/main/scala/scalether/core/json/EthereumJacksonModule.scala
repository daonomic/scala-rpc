package scalether.core.json

import java.math.BigInteger

import com.fasterxml.jackson.databind.module.SimpleModule
import scalether.domain.jackson._
import scalether.domain.request.TopicFilter

class EthereumJacksonModule extends SimpleModule {
  addDeserializer(classOf[BigInteger], BigIntegerHexDeserializer)
  addSerializer(classOf[BigInteger], BigIntegerHexSerializer)
  addSerializer(classOf[TopicFilter], new TopicFilterSerializer)
}
