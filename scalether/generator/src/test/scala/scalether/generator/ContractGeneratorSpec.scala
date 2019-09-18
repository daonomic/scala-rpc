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

  it should "generate IntegrationTest" taggedAs ManualTag in {
    println(generate("IntegrationTest"))
  }

  it should "generate id.IntegrationTest" taggedAs ManualTag in {
    println(generate("IntegrationTest", "scalether.test.id"))
  }

  def generate(name: String, packageName: String = "scalether.test"): String = {
    val json = Source.fromResource(s"$name.json").mkString
    val truffle = converter.readValue(json, classOf[TruffleContract])
    generator.generate(truffle, packageName, Type.SCALA)
  }
}
