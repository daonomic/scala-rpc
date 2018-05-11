package scalether.abi.array

import scalether.abi.{Decoded, Type}

import scala.reflect.ClassTag

class FixArrayType[T](length: Int, `type`: Type[T])
                     (implicit classTag: ClassTag[T])
  extends ArrayType[T](`type`) {

  assert(length != 0)

  override def size: Option[Int] = `type`.size.map(_ * length)

  def string = s"${`type`.string}[$length]"

  def decode(bytes: Array[Byte], offset: Int): Decoded[Array[T]] =
    decode(length, bytes, offset)
}

object FixArrayType {
  def apply[T](length: Int, `type`: Type[T])
              (implicit classTag: ClassTag[T]): FixArrayType[T] =
    new FixArrayType(length, `type`)
}
