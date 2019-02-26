package io.daonomic.rpc

import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.{DeserializationFeature, JsonNode, Module, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper

import scala.reflect.Manifest

class JsonConverter(modules: Module*) {
  val mapper = new ObjectMapper() with ScalaObjectMapper
  mapper.registerModule(DefaultScalaModule)
  for (module <- modules) {
    mapper.registerModule(module)
  }
  mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
  mapper.setSerializationInclusion(Include.NON_NULL)

  private def bigIntModule: SimpleModule = {
    val mod = new SimpleModule()
    mod
  }

  def toJson[A <: AnyRef](a: A): String =
    mapper.writeValueAsString(a)

  def fromJson[A <: AnyRef](json: String)(implicit mf: Manifest[A]): A =
    mapper.readValue(json)

  def fromJson[A <: AnyRef](json: JsonNode)(implicit mf: Manifest[A]): A =
    mapper.convertValue(json)
}
