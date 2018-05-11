package io.daonomic.blockchain.transfer;

import reactor.core.publisher.Mono;

public interface MonoTransferListener {
    Mono<Void> onTransfer(Transfer transfer, int confirmations, boolean confirmed);
}
