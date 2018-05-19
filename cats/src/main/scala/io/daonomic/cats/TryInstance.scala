package io.daonomic.cats

import scala.annotation.tailrec
import scala.util.{Failure, Success, Try}

class TryInstance extends MonadThrowable[Try] {
  def raiseError[A](e: Throwable): Try[A] = Failure(e)

  override def map[A, B](fa: Try[A])(f: A => B): Try[B] = fa.map(f)

  override def flatMap[A, B](fa: Try[A])(f: A => Try[B]): Try[B] = fa.flatMap(f)

  @tailrec final def tailRecM[B, C](b: B)(f: B => Try[Either[B, C]]): Try[C] =
    f(b) match {
      case f: Failure[_] => f.asInstanceOf[Try[C]]
      case Success(Left(b1)) => tailRecM(b1)(f)
      case Success(Right(c)) => Success(c)
    }

  override def pure[A](x: A): Try[A] = Success(x)
}
