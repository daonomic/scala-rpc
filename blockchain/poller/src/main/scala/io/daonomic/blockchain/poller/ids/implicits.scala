package io.daonomic.blockchain.poller.ids

import cats.Id
import io.daonomic.blockchain.poller.{Notifier, Poller}

object implicits {
  implicit val idSleeper: IdSleeper = new IdSleeper
  implicit val idPoller: Poller[Id] = new Poller[Id](idSleeper)
  implicit val idNotifier: Notifier[Id] = new IdNotifier
}
