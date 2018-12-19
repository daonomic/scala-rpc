package scalether.sync

import java.util.concurrent.{Callable, ConcurrentHashMap, Semaphore, TimeUnit}

import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import scalether.domain.Address

import scala.concurrent.duration.TimeUnit

class SemaphoreMonoSynchronizer(timeout: Long, unit: TimeUnit) extends MonoSynchronizer {
  private val semaphores = new ConcurrentHashMap[Address, Semaphore]()

  def this() {
    this(Long.MaxValue, TimeUnit.MILLISECONDS)
  }

  override def synchronize[V](address: Address)(execution: () => Mono[V]): Mono[V] = {
    val semaphore = getSemaphore(address)
    toMono[Boolean](() => semaphore.tryAcquire(timeout, unit))
        .flatMap[V] { acquired =>
          if (acquired) {
            execution()
              .doOnTerminate(() => semaphore.release())
          } else {
            Mono.error(new IllegalStateException("Timeout waiting for semaphore"))
          }
        }
  }

  private def toMono[T](callable: Callable[T]): Mono[T] =
    Mono.fromCallable(callable)
      .subscribeOn(Schedulers.elastic())

  private def getSemaphore(address: Address) =
    semaphores.computeIfAbsent(address, _ => new Semaphore(1))

}
