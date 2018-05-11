package scalether.core

import java.math.BigInteger
import java.util

import io.daonomic.cats.implicits._
import io.daonomic.rpc.MonoRpcTransport
import reactor.core.publisher.Mono
import scalether.domain.response.parity.Trace

import scala.collection.JavaConverters._

class MonoParity(transport: MonoRpcTransport)
  extends Parity[Mono](transport) {

  override def traceTransaction(txHash: String): Mono[List[Trace]] =
    super.traceTransaction(txHash)

  override def traceBlock(blockNumber: BigInteger): Mono[List[Trace]] =
    super.traceBlock(blockNumber)

  def traceTransactionJava(txHash: String): Mono[util.List[Trace]] =
    traceTransaction(txHash).map(_.asJava)

  def traceBlockJava(blockNumber: BigInteger): Mono[util.List[Trace]] =
    traceBlock(blockNumber).map(_.asJava)

}
