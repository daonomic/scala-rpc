package io.daonomic.blockchain

import java.math.BigInteger

import cats.Id
import io.daonomic.rpc.domain.Bytes

trait IdBlockchain extends Blockchain[Id] {
  override def blockNumber: BigInteger

  override def getTransactionIdsByBlock(block: BigInteger): List[Bytes]

  override def getTransactionsByBlock(block: BigInteger): List[Transaction]
}
