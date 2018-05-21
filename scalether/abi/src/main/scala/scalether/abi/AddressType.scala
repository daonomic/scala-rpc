package scalether.abi

import io.daonomic.rpc.domain.{Binary, Bytes}
import scalether.domain.Address

object AddressType extends Type[Address] {
  def string = "address"

  def encode(value: Address): Binary =
    value.padded

  def decode(data: Bytes, offset: Int): Decoded[Address] =
    Decoded(Address(data.slice(offset + 12, offset + 32)), offset + 32)
}
