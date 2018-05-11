package io.daonomic.blockchain.poller.futures

import scala.concurrent.Promise

class CompletePromiseRunnable[T](promise: Promise[T], value: T)
  extends Runnable {

  def run(): Unit = promise.success(value)
}
