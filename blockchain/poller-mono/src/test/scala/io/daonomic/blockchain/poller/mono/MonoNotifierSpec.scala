package io.daonomic.blockchain.poller.mono

import java.time.Duration

import org.scalatest.FlatSpec
import reactor.core.publisher.Mono

class MonoNotifierSpec extends FlatSpec {
  "MonoNotifier" should "work with large list" in {

    val large = (1 to 10000).toList

    val result = implicits.monoNotifier.notify(large)(print)
    result.block()
  }

  def print(i: Int): Mono[Unit] = {
    Mono.delay(Duration.ofMillis(0))
      .`then`(Mono.defer(() => {
        println(i)
        Mono.just()
      }))
  }
}
