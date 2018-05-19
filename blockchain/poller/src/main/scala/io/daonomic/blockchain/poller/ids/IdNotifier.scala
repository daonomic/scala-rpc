package io.daonomic.blockchain.poller.ids

import cats.Id
import io.daonomic.blockchain.poller.Notifier

class IdNotifier extends Notifier[Id] {
  override def notify[T](list: Seq[T])(f: T => Unit): Unit =
    list.foreach(f(_))
}
