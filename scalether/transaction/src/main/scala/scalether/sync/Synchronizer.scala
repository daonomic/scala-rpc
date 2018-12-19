package scalether.sync

import scalether.domain.Address

import scala.language.higherKinds

trait Synchronizer[F[_]] {
  def synchronized[T](address: Address)(execution: => F[T]): F[T]
}
