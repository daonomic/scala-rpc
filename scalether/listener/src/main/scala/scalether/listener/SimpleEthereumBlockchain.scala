package scalether.listener

import java.math.BigInteger

import cats.Monad
import cats.implicits._
import io.daonomic.blockchain.{BalanceChange, Blockchain, Transaction}
import io.daonomic.rpc.domain.Bytes
import scalether.core.Ethereum
import scalether.domain.response

import scala.language.higherKinds

class SimpleEthereumBlockchain[F[_]](ethereum: Ethereum[F])
                                    (implicit m: Monad[F])
  extends Blockchain[F] {

  override def getTransactionsByBlock(block: BigInteger): F[List[Transaction]] =
    ethereum.ethGetFullBlockByNumber(block)
      .map(block => {
        block.transactions
          .filter(tx => tx.to != null && tx.value != null && tx.value != BigInteger.ZERO)
          .map(tx => new SimpleEthereumTransaction(tx))
      })

  override def blockNumber: F[BigInteger] =
    ethereum.ethBlockNumber()

  override def getTransactionIdsByBlock(block: BigInteger): F[List[Bytes]] =
    ethereum.ethGetBlockByNumber(block)
      .map(_.transactions)
}

class SimpleEthereumTransaction(val tx: response.Transaction) extends Transaction {
  lazy val outputs: List[BalanceChange] = List(BalanceChange(tx.to.toString, tx.value))

  override def id: String = tx.hash.toString
}