package io.daonomic.blockchain.poller

import scala.language.higherKinds

trait Notifier[F[_]] {
  def notify[T](list: Seq[T])(f: T => F[Unit]): F[Unit]
}
