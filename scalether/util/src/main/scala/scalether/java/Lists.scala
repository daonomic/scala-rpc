package scalether.java

import scala.annotation.varargs
import scala.collection.JavaConverters._

object Lists {
  @varargs def toScala[T <: AnyRef](values: T*): List[T] = values.toList
  def toScala[T <: AnyRef](col: java.util.Collection[T]): List[T] = col.asScala.toList
  def toJava[T <: AnyRef](scala: List[T]): java.util.List[T] = scala.asJava
}
