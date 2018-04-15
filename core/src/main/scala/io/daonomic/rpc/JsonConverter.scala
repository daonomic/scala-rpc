package io.daonomic.rpc

import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.{DeserializationFeature, Module, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper

import scala.reflect.Manifest

class JsonConverter(modules: Module*) {
  val objectMapper = new ObjectMapper() with ScalaObjectMapper
  objectMapper.registerModule(DefaultScalaModule)
  for (module <- modules) {
    objectMapper.registerModule(module)
  }
  objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
  objectMapper.setSerializationInclusion(Include.NON_NULL)

  private def bigIntModule: SimpleModule = {
    val mod = new SimpleModule()
    mod
  }

  def toJson[A <: AnyRef](a: A): String =
    objectMapper.writeValueAsString(a)

  def fromJson[A <: AnyRef](json: String)(implicit mf: Manifest[A]): A =
    objectMapper.readValue(json)
}
