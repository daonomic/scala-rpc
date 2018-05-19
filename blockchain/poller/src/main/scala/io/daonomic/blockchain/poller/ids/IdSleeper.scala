package io.daonomic.blockchain.poller.ids

import cats.Id
import io.daonomic.blockchain.poller.Sleeper

class IdSleeper extends Sleeper[Id] {
  def sleep(sleep: Long): Unit =
    Thread.sleep(sleep)
}
