package io.daonomic.blockchain.common

import java.math.BigInteger

import cats.Monad
import cats.implicits._
import io.daonomic.blockchain.poller.Notifier
import io.daonomic.blockchain.state.State
import org.slf4j.{Logger, LoggerFactory}

import scala.language.higherKinds

abstract class AbstractListenService[F[_]](confidence: Int, state: State[BigInteger, F])
                                          (implicit m: Monad[F], n: Notifier[F]) {

  protected val logger: Logger = LoggerFactory.getLogger(getClass)

  def check(blockNumber: BigInteger): F[Unit] = for {
    saved <- state.get
    _ <- fetchAndNotify(blockNumber, saved)
  } yield ()

  private def fetchAndNotify(blockNumber: BigInteger, saved: Option[BigInteger]): F[Unit] = {
    logger.info(s"fetchAndNotify saved=$saved block=$blockNumber")
    val from = saved.getOrElse(blockNumber.subtract(BigInteger.ONE))
    val start = from.subtract(BigInteger.valueOf(confidence - 1))
    val numbers = blockNumbers(start, blockNumber)
    logger.info(s"will fetchAndNotify blocks: $numbers")
    n.notify(numbers) { num =>
      fetchAndNotify(blockNumber)(num.bigInteger).flatMap(_ => state.set(num.bigInteger))
    }
  }

  private def blockNumbers(from: BigInt, to: BigInt): Seq[BigInt] = {
    if (from > to)
      Nil
    else if (from < 0)
      BigInt(0) to to
    else
      from to to
  }

  protected def fetchAndNotify(latestBlock: BigInteger)(block: BigInteger): F[Unit]
}
