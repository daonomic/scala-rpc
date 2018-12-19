package scalether.sync

import java.util.concurrent.{ConcurrentHashMap, Semaphore, TimeUnit}

import scalether.domain.Address

import scala.concurrent.duration.TimeUnit
import scala.util.{Failure, Try}

class SemaphoreTrySynchronizer(timeout: Long, unit: TimeUnit) extends TrySynchronizer {
  private val semaphores = new ConcurrentHashMap[Address, Semaphore]()

  def this() {
    this(Long.MaxValue, TimeUnit.MILLISECONDS)
  }

  override def synchronize[T](address: Address)(execution: () => Try[T]): Try[T] = {
    val semaphore = getSemaphore(address)
    if (!semaphore.tryAcquire(timeout, unit)) {
      Failure(new IllegalStateException("Timeout waiting for lock"))
    } else {
      try {
        execution()
      } finally {
        semaphore.release()
      }
    }
  }

  private def getSemaphore(address: Address) =
    semaphores.computeIfAbsent(address, _ => new Semaphore(1))

}
