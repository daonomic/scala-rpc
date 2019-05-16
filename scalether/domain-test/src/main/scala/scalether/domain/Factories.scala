package scalether.domain

import io.daonomic.rpc.domain.{Binary, BinaryFactory, Word, WordFactory}

object Factories {
  def randomAddress(): Address = AddressFactory.create()
  def randomBin(size: Int): Binary = BinaryFactory.create(size)
  def randomWord(): Word = WordFactory.create()
}
