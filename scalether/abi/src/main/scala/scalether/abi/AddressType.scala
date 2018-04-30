package scalether.abi

import scalether.domain.Address

object AddressType extends Type[Address] {
  def string = "address"

  def encode(value: Address): Array[Byte] =
    value.padded

  def decode(bytes: Array[Byte], offset: Int): Decoded[Address] =
    Decoded(Address(bytes.slice(offset + 12, offset + 32)), offset + 32)
}
