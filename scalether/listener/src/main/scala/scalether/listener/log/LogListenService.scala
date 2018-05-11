package scalether.listener.log

import java.math.BigInteger

import cats.Monad
import cats.implicits._
import io.daonomic.blockchain.state.State
import org.slf4j.{Logger, LoggerFactory}
import scalether.core.Ethereum
import scalether.domain.response.Log
import scalether.util.Hex

import scala.language.higherKinds

class LogListenService[F[_]](ethereum: Ethereum[F],
                             confidence: Int,
                             listener: LogListener[F],
                             state: State[BigInteger, F])
                            (implicit m: Monad[F]) {

  val logger: Logger = LoggerFactory.getLogger(getClass)

  def check(blockNumber: BigInteger): F[List[Log]] = if (listener.enabled) {
    checkInternal(blockNumber)
  } else {
    if (logger.isDebugEnabled()) {
      logger.debug("listener is disabled. ignoring")
    }
    m.pure(Nil)
  }

  private def checkInternal(blockNumber: BigInteger): F[List[Log]] = for {
    saved <- state.get
    logs <- fetchLogs(blockNumber, saved)
    _ <- notifyListeners(blockNumber, logs)
    _ <- updateSettingIfChanged(saved, blockNumber)
  } yield logs

  private def fetchLogs(blockNumber: BigInteger, savedBlockNumber: Option[BigInteger]): F[List[Log]] = {
    if (logger.isDebugEnabled()) {
      logger.debug(s"fetchLogs blockNumber=$blockNumber savedBlockNumber=$savedBlockNumber")
    }
    if (savedBlockNumber.contains(blockNumber)) {
      m.pure(Nil)
    } else {
      val fromBlock = savedBlockNumber match {
        case Some(value) => value.subtract(BigInteger.valueOf(confidence - 2))
        case None => BigInteger.ZERO
      }
      if (logger.isDebugEnabled()) {
        logger.debug(s"createFilter from=$fromBlock to=$blockNumber")
      }
      for {
        filter <- listener.createFilter(Hex.prefixed(checkForZero(fromBlock)), Hex.prefixed(blockNumber))
        logs <- ethereum.ethGetLogs(filter)
      } yield logs
    }
  }

  private def updateSettingIfChanged(savedBlockNumber: Option[BigInteger], current: BigInteger): F[Unit] = {
    if (savedBlockNumber.contains(current)) {
      m.unit
    } else {
      state.set(current)
    }
  }

  private def checkForZero(value: BigInteger): BigInteger =
    if (value.compareTo(BigInteger.ZERO) < 0) BigInteger.ZERO else value

  private def notifyListeners(blockNumber: BigInteger, logs: List[Log]): F[Unit] =
    logs.foldLeft(m.unit)((monad, log) => monad.flatMap(_ => notifyListener(blockNumber, log)))

  private def notifyListener(blockNumber: BigInteger, log: Log): F[Unit] = {
    val confirmations = blockNumber.subtract(log.blockNumber).intValue() + 1
    listener.onLog(log, confirmations, confirmations >= confidence)
  }
}
