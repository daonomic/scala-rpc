package io.daonomic.blockchain.transaction;

import reactor.core.publisher.Mono;

import java.math.BigInteger;

public interface MonoTransactionListener {
    Mono<Void> onTransaction(String transactionHash, BigInteger blockNumber, int confirmations, boolean confirmed);
}
