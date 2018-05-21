package scalether.abi.array

import java.math.BigInteger

import io.daonomic.rpc.domain
import io.daonomic.rpc.domain.Binary
import scalether.abi.{Decoded, Type, Uint256Type}

import scala.reflect.ClassTag

class VarArrayType[T](val `type`: Type[T])
                     (implicit classTag: ClassTag[T])
  extends ArrayType[T](`type`) {

  override def size: Option[Int] = None

  def string = s"${`type`.string}[]"

  override def encode(value: Array[T]): Binary =
    Uint256Type.encode(BigInteger.valueOf(value.length)) ++ super.encode(value)

  def decode(data: domain.Bytes, offset: Int): Decoded[Array[T]] = {
    val length = Uint256Type.decode(data, offset)
    decode(length.value.intValue(), data, length.offset)
  }
}

object VarArrayType {
  def apply[T](`type`: Type[T])
              (implicit classTag: ClassTag[T]): VarArrayType[T] =
    new VarArrayType[T](`type`)
}