package io.daonomic.cats

import cats.Id

class IdInstance extends MonadThrowable[Id] {
  override def raiseError[A](e: Throwable): Id[A] = throw e

  override def map[A, B](fa: Id[A])(f: A => B): Id[B] = f(fa)

  override def flatMap[A, B](fa: Id[A])(f: A => Id[B]): Id[B] = f(fa)

  override def tailRecM[A, B](a: A)(f: A => Id[Either[A, B]]): Id[B] = ???

  override def pure[A](x: A): Id[A] = x
}
