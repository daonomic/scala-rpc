package scalether.test

import java.math.BigInteger

import cats.implicits._
import org.scalacheck.Gen
import org.scalatest.FlatSpec
import org.scalatest.prop.PropertyChecks
import scalether.domain.Address
import scalether.domain.implicits._
import scalether.domain.request.Transaction

class TransactionSignerIntegrationSpec extends FlatSpec with PropertyChecks with IntegrationSpec {

  val addressAndValue: Gen[(Address, BigInteger)] = for {
    address <- Generators.address
    value <- Gen.chooseNum[Long](0, 100000)
  } yield (address, BigInteger.valueOf(value))

  "Signer" should "sign simple ether transaction" in {
    forAll(addressAndValue) {
      case (address, value) =>
        poller.waitForTransaction(sender.sendTransaction(Transaction(to = address, value = value)))
        assert(ethereum.ethGetBalance(address, "latest").get == value)
    }
  }

  it should "sign contract deployments and calls" in {
    forAll(Gen.posNum[Long]) { value =>
      val test = IntegrationTest.deployAndWait(sender, poller).get
      poller.waitForTransaction(test.setState(value).execute())
      assert(test.state.get == (value: BigInteger))
    }
  }
}
