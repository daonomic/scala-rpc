package io.daonomic.rpc

import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.fasterxml.jackson.databind.{DeserializationFeature, JsonNode, Module, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper

import scala.reflect.Manifest

class JsonConverter(modules: Module*) {
  val mapper: ObjectMapper with ScalaObjectMapper = JsonConverter.createMapper(modules: _*)

  def toJson[A](a: A): String =
    mapper.writeValueAsString(a)

  def fromJson[A](json: String)(implicit mf: Manifest[A]): A =
    mapper.readValue(json)

  def fromJson[A](json: JsonNode)(implicit mf: Manifest[A]): A =
    mapper.convertValue(json)
}

object JsonConverter {
  def createMapper(modules: Module*): ObjectMapper with ScalaObjectMapper = {
    val mapper = new ObjectMapper() with ScalaObjectMapper
    mapper.registerModule(DefaultScalaModule)
    for (module <- modules) {
      mapper.registerModule(module)
    }
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    mapper.setSerializationInclusion(Include.NON_NULL)
    mapper
  }
}