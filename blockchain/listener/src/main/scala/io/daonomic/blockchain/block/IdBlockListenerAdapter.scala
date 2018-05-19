package io.daonomic.blockchain.block

import java.math.BigInteger

import cats.Id

class IdBlockListenerAdapter(listener: IdBlockListener) extends BlockListener[Id] {
  override def onBlock(block: BigInteger): Unit =
    listener.onBlock(block)
}
