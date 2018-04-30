package scalether.abi

trait Type[T] {
  def string: String

  def size: Option[Int] = Some(32)

  def dynamic: Boolean = size.isEmpty

  def encode(value: T): Array[Byte]

  def decode(bytes: Array[Byte], offset: Int): Decoded[T]
}
