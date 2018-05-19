package io.daonomic.blockchain.state;

public interface IdState<T> {
    T get();
    void set(T value);
}
