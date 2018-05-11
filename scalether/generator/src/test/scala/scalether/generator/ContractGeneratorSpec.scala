package scalether.generator

import com.fasterxml.jackson.databind.ObjectMapper
import io.daonomic.rpc.ManualTag
import org.scalatest.FlatSpec
import scalether.generator.domain.{TruffleContract, Type}

import scala.io.Source

class ContractGeneratorSpec extends FlatSpec {
  val converter: ObjectMapper = ContractGenerator.createMapper()
  val generator = new ContractGenerator

  "Generator" should "generate IssuedToken" taggedAs ManualTag in {
    println(generate("IssuedToken"))
  }

  it should "generate Token" taggedAs ManualTag in {
    println(generate("Token"))
  }

  def generate(name: String): String = {
    val json = Source.fromResource(s"$name.json").mkString
    val truffle = converter.readValue(json, classOf[TruffleContract])
    generator.generate(truffle, "org.daomao.contract", Type.SCALA)
  }
}
