package scalether.listener.log

import java.math.BigInteger
import java.util

import io.daonomic.blockchain.state.{MonoState, MonoStateAdapter}
import io.daonomic.cats.implicits._
import reactor.core.publisher.Mono
import scalether.core.MonoEthereum
import scalether.domain.response.Log

import scala.collection.JavaConverters._

class MonoLogListenService(ethereum: MonoEthereum, confidence: Int, listener: MonoLogListener, monoState: MonoState[BigInteger]) {
  private val scala = new LogListenService[Mono](ethereum, confidence, new MonoLogListenerAdapter(listener), new MonoStateAdapter[BigInteger](monoState))

  def checkAndGetJava(blockNumber: BigInteger): Mono[util.List[Log]] =
    scala.checkAndGet(blockNumber).map(scalaList => scalaList.asJava)
}
