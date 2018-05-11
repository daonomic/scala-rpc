package scalether.listener

import java.math.BigInteger

import cats.Monad
import cats.implicits._
import io.daonomic.blockchain.{BalanceChange, Blockchain, Transaction}
import scalether.core.{Ethereum, Parity}
import scalether.domain.response.parity.Trace

import scala.language.higherKinds

class EthereumBlockchain[F[_]](ethereum: Ethereum[F], parity: Parity[F])
                              (implicit m: Monad[F])
  extends Blockchain[F] {

  private def filterTraces(list: List[Trace]): List[Trace] =
    list.filter(_.`type` != "reward")
      .filter(_.error == null)
      .filter(_.action != null)
      .filter(_.action.to != null)
      .filter(_.action.from != null)
      .filter(_.transactionHash != null)
      .filter(_.action.value != BigInteger.ZERO)

  override def getTransactionsByBlock(block: BigInteger): F[List[Transaction]] =
    parity.traceBlock(block)
      .map(traces => filterTraces(traces).groupBy(_.transactionHash.toString))
      .map(_.toList.map { case (hash, traces) => new EthereumTransaction(hash, traces) })

  override def blockNumber: F[BigInteger] =
    ethereum.ethBlockNumber()

  override def getTransactionIdsByBlock(block: BigInteger): F[List[String]] =
    ethereum.ethGetBlockByNumber(block)
    .map(_.transactions.map(_.toString))
}

class EthereumTransaction(val id: String, traces: List[Trace]) extends Transaction {
  lazy val outputs: List[BalanceChange] = traces
    .map(t => BalanceChange(t.action.to.toString, t.action.value))
}