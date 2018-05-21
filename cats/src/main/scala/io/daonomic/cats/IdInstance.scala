package io.daonomic.cats

import cats.Id

import scala.annotation.tailrec

class IdInstance extends MonadThrowable[Id] {
  override def raiseError[A](e: Throwable): Id[A] = throw e

  override def map[A, B](fa: Id[A])(f: A => B): Id[B] = f(fa)

  override def flatMap[A, B](fa: Id[A])(f: A => Id[B]): Id[B] = f(fa)

  @tailrec override final def tailRecM[A, B](a: A)(f: A => Either[A, B]): B = f(a) match {
    case Left(a1) => tailRecM(a1)(f)
    case Right(b) => b
  }

  override def pure[A](x: A): Id[A] = x
}
