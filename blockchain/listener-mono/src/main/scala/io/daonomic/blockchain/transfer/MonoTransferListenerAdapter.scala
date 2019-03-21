package io.daonomic.blockchain.transfer

import reactor.core.publisher.Mono

class MonoTransferListenerAdapter(listener: MonoTransferListener) extends TransferListener[Mono] {
  override def onTransfer(transfer: Transfer, confirmations: Int, confirmed: Boolean): Mono[Unit] =
    listener.onTransfer(transfer, confirmations, confirmed)
      .`then`(Mono.just())
}
