package io.daonomic.blockchain.block

import java.math.BigInteger

import reactor.core.publisher.Mono

class MonoBlockListenerAdapter(listener: MonoBlockListener) extends BlockListener[Mono] {
  override def onBlock(block: BigInteger): Mono[Unit] =
    listener.onBlock(block)
      .`then`(Mono.just())
}
