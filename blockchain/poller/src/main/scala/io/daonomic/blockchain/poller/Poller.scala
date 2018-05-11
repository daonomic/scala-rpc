package io.daonomic.blockchain.poller

import cats.Monad
import cats.implicits._

import scala.language.higherKinds

class Poller[F[_]](sleeper: Sleeper[F])(implicit m: Monad[F]) {
  def poll[T](sleep: Long)(poller: => F[Option[T]]): F[T] =
    poller flatMap (_.map(m.pure).getOrElse(poll(sleep)(poller)))
}
