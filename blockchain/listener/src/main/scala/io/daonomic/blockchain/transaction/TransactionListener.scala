package io.daonomic.blockchain.transaction

import java.math.BigInteger

import scala.language.higherKinds

trait TransactionListener[F[_]] {
  def onTransaction(id: String, blockNumber: BigInteger, confirmations: Int, confirmed: Boolean): F[Unit]
}
