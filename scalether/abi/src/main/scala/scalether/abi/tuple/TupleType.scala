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
    case 1 => Tuple1Type(types.head)
    case 2 => Tuple2Type(types.head, types(1))
    case 3 => Tuple3Type(types.head, types(1), types(2))
    case 4 => Tuple4Type(types.head, types(1), types(2), types(3))
    case 5 => Tuple5Type(types.head, types(1), types(2), types(3), types(4))
    case 6 => Tuple6Type(types.head, types(1), types(2), types(3), types(4), types(5))
    case 7 => Tuple7Type(types.head, types(1), types(2), types(3), types(4), types(5), types(6))
    case 8 => Tuple8Type(types.head, types(1), types(2), types(3), types(4), types(5), types(6), types(7))
    case 9 => Tuple9Type(types.head, types(1), types(2), types(3), types(4), types(5), types(6), types(7), types(8))
    case 10 => Tuple10Type(types.head, types(1), types(2), types(3), types(4), types(5), types(6), types(7), types(8), types(9))
    case 11 => Tuple11Type(types.head, types(1), types(2), types(3), types(4), types(5), types(6), types(7), types(8), types(9), types(10))
    case 12 => Tuple12Type(types.head, types(1), types(2), types(3), types(4), types(5), types(6), types(7), types(8), types(9), types(10), types(11))
    case 13 => Tuple13Type(types.head, types(1), types(2), types(3), types(4), types(5), types(6), types(7), types(8), types(9), types(10), types(11), types(12))
    case 14 => Tuple14Type(types.head, types(1), types(2), types(3), types(4), types(5), types(6), types(7), types(8), types(9), types(10), types(11), types(12), types(13))
    case 15 => Tuple15Type(types.head, types(1), types(2), types(3), types(4), types(5), types(6), types(7), types(8), types(9), types(10), types(11), types(12), types(13), types(14))
    case 16 => Tuple16Type(types.head, types(1), types(2), types(3), types(4), types(5), types(6), types(7), types(8), types(9), types(10), types(11), types(12), types(13), types(14), types(15))
    case 17 => Tuple17Type(types.head, types(1), types(2), types(3), types(4), types(5), types(6), types(7), types(8), types(9), types(10), types(11), types(12), types(13), types(14), types(15), types(16))
    case 18 => Tuple18Type(types.head, types(1), types(2), types(3), types(4), types(5), types(6), types(7), types(8), types(9), types(10), types(11), types(12), types(13), types(14), types(15), types(16), types(17))
    case 19 => Tuple19Type(types.head, types(1), types(2), types(3), types(4), types(5), types(6), types(7), types(8), types(9), types(10), types(11), types(12), types(13), types(14), types(15), types(16), types(17), types(18))
    case 20 => Tuple20Type(types.head, types(1), types(2), types(3), types(4), types(5), types(6), types(7), types(8), types(9), types(10), types(11), types(12), types(13), types(14), types(15), types(16), types(17), types(18), types(19))
    case 21 => Tuple21Type(types.head, types(1), types(2), types(3), types(4), types(5), types(6), types(7), types(8), types(9), types(10), types(11), types(12), types(13), types(14), types(15), types(16), types(17), types(18), types(19), types(20))
    case 22 => Tuple22Type(types.head, types(1), types(2), types(3), types(4), types(5), types(6), types(7), types(8), types(9), types(10), types(11), types(12), types(13), types(14), types(15), types(16), types(17), types(18), types(19), types(20), types(21))
    case _ => throw new IllegalArgumentException(s"types.size not supported: ${types.size}")
  }
}