package io.daonomic.rpc.domain

object WordFactory {
  def create(): Word = Word.apply(BinaryFactory.createByteArray(32))
}
