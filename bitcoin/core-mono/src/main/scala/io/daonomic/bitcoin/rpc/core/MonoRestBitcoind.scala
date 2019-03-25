package io.daonomic.bitcoin.rpc.core

import io.daonomic.bitcoin.rpc.domain.{Block, Transaction}
import io.daonomic.rpc.MonoHttpTransport
import io.daonomic.rpc.domain.Binary
import reactor.core.publisher.Mono

class MonoRestBitcoind(transport: MonoHttpTransport) extends RestBitcoind[Mono](transport) {
  override def getBlockSimple(hash: String): Mono[Block[Binary]] =
    super.getBlockSimple(hash)

  override def getBlockFull(hash: String): Mono[Block[Transaction]] =
    super.getBlockFull(hash)
}
