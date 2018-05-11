package scalether.abi.array

import java.math.BigInteger

import scalether.abi.{Decoded, Type, Uint256Type}

import scala.reflect.ClassTag

class VarArrayType[T](`type`: Type[T])
                     (implicit classTag: ClassTag[T])
  extends ArrayType[T](`type`) {

  override def size = None

  def string = s"${`type`.string}[]"

  override def encode(value: Array[T]): Array[Byte] =
    Uint256Type.encode(BigInteger.valueOf(value.length)) ++ super.encode(value)

  def decode(bytes: Array[Byte], offset: Int): Decoded[Array[T]] = {
    val length = Uint256Type.decode(bytes, offset)
    decode(length.value.intValue(), bytes, length.offset)
  }
}

object VarArrayType {
  def apply[T](`type`: Type[T])
              (implicit classTag: ClassTag[T]): VarArrayType[T] =
    new VarArrayType[T](`type`)
}