package io.daonomic.blockchain.poller.tries

import io.daonomic.blockchain.poller.Sleeper

import scala.util.Try

class TrySleeper extends Sleeper[Try] {
  def sleep(sleep: Long): Try[Unit] = Try {
    Thread.sleep(sleep)
  }
}
