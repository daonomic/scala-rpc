package io.daonomic.blockchain.block

import java.math.BigInteger

import cats.Id
import io.daonomic.blockchain.IdBlockchain
import io.daonomic.blockchain.state.{IdState, IdStateAdapter}
import io.daonomic.cats.implicits._

class IdBlockListenService(blockchain: IdBlockchain, listener: IdBlockListener, state: IdState[BigInteger]) {
  private val scala = new BlockListenService[Id](blockchain, new IdBlockListenerAdapter(listener), new IdStateAdapter[BigInteger](state))

  def check(): BigInteger =
    scala.check()
}
