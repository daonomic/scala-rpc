package scalether.sync

import cats.Id
import scalether.domain.Address

trait IdSynchronizer extends Synchronizer[Id] {
  override def synchronized[T](address: Address)(execution: => T): T
}
