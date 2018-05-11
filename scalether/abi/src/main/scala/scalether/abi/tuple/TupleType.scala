package scalether.abi.tuple

import scalether.abi.Type

abstract class TupleType[T] extends Type[T] {

  override def size: Option[Int] = types.map(_.size).foldLeft(Option(0)) {
    (acc, el) => el.flatMap(value => acc.map(sum => sum + value))
  }

  def types: List[Type[_]]

  lazy val headSize: Int = types.map(_.size.getOrElse(32)).sum

  def headOffset(idx: Int): Int =
    (0 until idx).map(i => types(i).size.getOrElse(32)).sum
}
