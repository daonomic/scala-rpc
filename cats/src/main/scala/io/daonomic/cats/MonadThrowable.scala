package io.daonomic.cats

import cats.Monad

import scala.language.higherKinds

trait MonadThrowable[F[_]] extends Monad[F] {
  def raiseError[A](e: Throwable): F[A]
}
