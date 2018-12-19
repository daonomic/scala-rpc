package scalether.sync

import scalether.domain.Address

import scala.util.Try

trait TrySynchronizer extends Synchronizer[Try] {
  override def synchronize[T](address: Address)(execution: () => Try[T]): Try[T]
}
