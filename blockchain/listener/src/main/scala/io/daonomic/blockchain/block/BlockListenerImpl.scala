package io.daonomic.blockchain.block

import java.math.BigInteger

import cats.implicits._
import cats.Monad
import io.daonomic.blockchain.common.ListenService

import scala.language.higherKinds

class BlockListenerImpl[F[_]](services: ListenService[F]*)
                             (implicit m: Monad[F])
  extends BlockListener[F] {

  override def onBlock(block: BigInteger): F[Unit] =
    services.toList.foldLeft(m.unit) { (prev, s) =>
      prev.flatMap(_ => s.check(block))
    }
}
