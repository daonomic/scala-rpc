package io.daonomic.blockchain

import java.math.BigInteger

import io.daonomic.rpc.domain.Bytes
import reactor.core.publisher.Mono

trait MonoBlockchain extends Blockchain[Mono] {
  override def blockNumber: Mono[BigInteger]

  override def getTransactionIdsByBlock(block: BigInteger): Mono[List[Bytes]]

  override def getTransactionsByBlock(block: BigInteger): Mono[List[Transaction]]
}
