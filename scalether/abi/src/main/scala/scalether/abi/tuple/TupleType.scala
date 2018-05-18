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

object TupleType {
  def apply(types: List[Type[_]]): TupleType[_] = types.size match {
    case 0 => UnitType
    case i if i > 0 && i <= 22 =>
      Class.forName(s"scalether.abi.tuple.Tuple${i}Type").getConstructors.head.newInstance(types:_*).asInstanceOf[TupleType[_]]
    case _ => throw new IllegalArgumentException(s"types.size not supported: ${types.size}")
  }
}