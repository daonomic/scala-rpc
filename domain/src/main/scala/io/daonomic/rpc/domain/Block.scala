package io.daonomic.rpc.domain

import java.math.BigInteger

trait Block {
  def getBlockNumber: BigInteger
  def getBlockHash: Bytes
}
