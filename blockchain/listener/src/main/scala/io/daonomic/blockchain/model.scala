package io.daonomic.blockchain

import java.math.BigInteger

import scala.language.higherKinds

trait Blockchain[F[_]] {
  def blockNumber: F[BigInteger]
  def getTransactionIdsByBlock(block: BigInteger): F[List[String]]
  def getTransactionsByBlock(block: BigInteger): F[List[Transaction]]
}

trait Transaction {
  def id: String
  def outputs: List[BalanceChange]
}

case class BalanceChange(address: String, value: BigInteger)
