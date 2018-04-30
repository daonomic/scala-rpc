package scalether.test

import cats.implicits._
import io.daonomic.rpc.tries.ScalajHttpTransport
import scalether.core.Ethereum
import scalether.domain.implicits._
import io.daonomic.blockchain.poller.tries.implicits._
import scalether.transaction._

import scala.util.Try

trait IntegrationSpec {
  val ethereum = new Ethereum[Try](new ScalajHttpTransport("http://localhost:8545"))
  val sender = new SigningTransactionSender[Try](
    ethereum,
    new SimpleNonceProvider[Try](ethereum),
    org.web3j.utils.Numeric.toBigInt("0x00120de4b1518cf1f16dc1b02f6b4a8ac29e870174cb1d8575f578480930250a"),
    2000000,
    new ValGasPriceProvider[Try](10)
  )
  val poller:TransactionPoller[Try] = new TransactionPoller(ethereum)
}
