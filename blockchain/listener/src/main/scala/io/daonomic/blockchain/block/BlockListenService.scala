package io.daonomic.blockchain.block

import java.math.BigInteger

import cats.Monad
import cats.implicits._
import io.daonomic.blockchain.Blockchain
import io.daonomic.blockchain.state.State

import scala.language.higherKinds

class BlockListenService[F[_]](blockchain: Blockchain[F], listener: BlockListener[F], state: State[BigInteger, F])
                              (implicit m: Monad[F]) {

  def check(): F[BigInteger] = for {
    blockNumber <- blockchain.blockNumber
    savedValue <- state.get
    changed <- notifyListener(blockNumber, savedValue)
    _ <- setState(blockNumber, changed)
  } yield blockNumber

  private def notifyListener(blockNumber: BigInteger, savedValue: Option[BigInteger]): F[Boolean] = savedValue match {
    case Some(value) if value == blockNumber => m.pure(false)
    case _ => listener.onBlock(blockNumber).map(_ => true)
  }

  private def setState(blockNumber: BigInteger, changed: Boolean): F[Unit] =
    if (changed)
      state.set(blockNumber)
    else
      m.unit

}
