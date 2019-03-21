package scalether.sync

import reactor.core.publisher.Mono
import scalether.domain.Address

trait MonoSynchronizer extends Synchronizer[Mono] {
  override def synchronize[T](address: Address)(execution: () => Mono[T]): Mono[T]
}
