package scalether.domain

import io.daonomic.rpc.domain.BinaryFactory

object AddressFactory {
  def create() = Address(BinaryFactory.createByteArray(20))
}
