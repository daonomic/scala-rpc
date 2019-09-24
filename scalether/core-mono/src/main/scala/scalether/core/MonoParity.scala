package scalether.core

import java.math.BigInteger
import java.util

import io.daonomic.cats.mono.implicits._
import io.daonomic.rpc.MonoRpcTransport
import reactor.core.publisher.Mono
import scalether.domain.request.Transaction
import scalether.domain.response.parity.{Trace, TraceResult}

import scala.collection.JavaConverters._

class MonoParity(transport: MonoRpcTransport)
  extends Parity[Mono](transport) {

  override def traceTransaction(txHash: String): Mono[List[Trace]] =
    super.traceTransaction(txHash)

  override def traceBlock(blockNumber: BigInteger): Mono[List[Trace]] =
    super.traceBlock(blockNumber)

  override def traceCallMany(transactions: List[Transaction], defaultBlockParameter: String): Mono[List[TraceResult]] =
    super.traceCallMany(transactions, defaultBlockParameter)

  def traceTransactionJava(txHash: String): Mono[util.List[Trace]] =
    traceTransaction(txHash).map(_.asJava)

  def traceBlockJava(blockNumber: BigInteger): Mono[util.List[Trace]] =
    traceBlock(blockNumber).map(_.asJava)

  def traceCallManyJava(transactions: util.List[Transaction], defaultBlockParameter: String): Mono[util.List[TraceResult]] =
    traceCallMany(transactions.asScala.toList, defaultBlockParameter).map(_.asJava)

}
