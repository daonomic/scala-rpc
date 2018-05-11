package io.daonomic.blockchain.poller.futures

import cats.implicits._
import io.daonomic.blockchain.poller.Poller

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object implicits {
  implicit val futureSleeper: FutureSleeper = new FutureSleeper
  implicit val futurePoller: Poller[Future] = new Poller[Future](futureSleeper)
}
