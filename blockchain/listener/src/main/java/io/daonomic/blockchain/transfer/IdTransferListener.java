package io.daonomic.blockchain.transfer;

public interface IdTransferListener {
    void onTransfer(Transfer transfer, int confirmations, boolean confirmed);
}
