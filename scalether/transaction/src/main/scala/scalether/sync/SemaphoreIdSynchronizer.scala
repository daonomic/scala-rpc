package scalether.sync

import java.util.concurrent.{ConcurrentHashMap, Semaphore, TimeUnit}

import scalether.domain.Address

import scala.concurrent.duration.TimeUnit

class SemaphoreIdSynchronizer(timeout: Long, unit: TimeUnit) extends IdSynchronizer {
  private val semaphores = new ConcurrentHashMap[Address, Semaphore]()

  def this() {
    this(Long.MaxValue, TimeUnit.MILLISECONDS)
  }

  override def synchronize[T](address: Address)(execution: () => T): T = {
    val semaphore = getSemaphore(address)
    if (!semaphore.tryAcquire(timeout, unit)) {
      throw new IllegalStateException("Timeout waiting for lock")
    }
    try {
      execution()
    } finally {
      semaphore.release()
    }
  }

  private def getSemaphore(address: Address) =
    semaphores.computeIfAbsent(address, _ => new Semaphore(1))

}
