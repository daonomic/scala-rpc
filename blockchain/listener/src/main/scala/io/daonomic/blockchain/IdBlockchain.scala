package io.daonomic.blockchain

import java.math.BigInteger

import cats.Id

trait IdBlockchain extends Blockchain[Id] {
  override def blockNumber: BigInteger

  override def getTransactionIdsByBlock(block: BigInteger): List[String]

  override def getTransactionsByBlock(block: BigInteger): List[Transaction]
}
