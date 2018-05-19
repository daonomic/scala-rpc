package io.daonomic.blockchain.transaction;

import java.math.BigInteger;

public interface IdTransactionListener {
    void onTransaction(String transactionHash, BigInteger blockNumber, int confirmations, boolean confirmed);
}
