package scalether.core

import java.math.BigInteger
import java.util

import cats.Id
import io.daonomic.cats.implicits._
import io.daonomic.rpc.IdRpcTransport
import scalether.domain.request.Transaction
import scalether.domain.response.parity.{Trace, TraceResult}

import scala.collection.JavaConverters._

class IdParity(transport: IdRpcTransport)
  extends Parity[Id](transport) {

  override def traceTransaction(txHash: String): List[Trace] =
    super.traceTransaction(txHash)

  override def traceBlock(blockNumber: BigInteger): List[Trace] =
    super.traceBlock(blockNumber)

  override def traceCallMany(transactions: List[Transaction], defaultBlockParameter: String): Id[List[TraceResult]] =
    super.traceCallMany(transactions, defaultBlockParameter)

  def traceTransactionJava(txHash: String): util.List[Trace] =
    traceTransaction(txHash).asJava

  def traceBlockJava(blockNumber: BigInteger): util.List[Trace] =
    traceBlock(blockNumber).asJava

}
