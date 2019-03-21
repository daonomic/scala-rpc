package io.daonomic.blockchain.poller.mono

import io.daonomic.blockchain.poller.Notifier
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

class MonoNotifier extends Notifier[Mono] {
  private val scheduler = Schedulers.newSingle("mono-notifier")

  override def notify[T](list: Seq[T])(f: T => Mono[Unit]): Mono[Unit] =
    MonoReduce.reduce[T, Unit]((), list) { (_, t) =>
      f(t)
    }
}
