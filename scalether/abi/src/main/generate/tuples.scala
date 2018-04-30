import java.io.{File, FileOutputStream, PrintWriter}

def generate(arity: Int, writer: PrintWriter): Unit = {
  val range = 1 to arity

  writer.println("package scalether.abi.tuple")
  writer.println()
  writer.println("import java.math.BigInteger")
  writer.println()
  writer.println("import scalether.abi.{Decoded, Type, Uint256Type}")
  writer.println()
  writer.println("import scala.collection.mutable.ListBuffer")
  writer.println()
  val ts = range.map(i => s"T$i").mkString(", ")
  writer.println(s"class Tuple${arity}Type[$ts](${range.map(i => s"val type$i: Type[T$i]").mkString(", ")}) extends TupleType[($ts)] {")
  writer.println("  def string = s\"(" + range.map(i => "${type" + i + ".string}").mkString(",") + ")\"")
  writer.println()
  writer.println(s"  def types = List(${range.map(i => "type" + i).mkString(", ")})")
  writer.println()
  writer.println(s"  def encode(value: ($ts)): Array[Byte] = {")
  writer.println(s"    val head = ListBuffer[Byte]()")
  writer.println(s"    val tail = ListBuffer[Byte]()")
  for (i <- range) {
    writer.println(s"    if (type$i.dynamic) {")
    writer.println(s"      head ++= Uint256Type.encode(BigInteger.valueOf(headSize + tail.size))")
    writer.println(s"      tail ++= type$i.encode(value._$i)")
    writer.println(s"    } else {")
    writer.println(s"      head ++= type$i.encode(value._$i)")
    writer.println(s"    } ")
  }
  writer.println(s"    (head ++ tail).toArray")
  writer.println(s"  }")
  writer.println()
  writer.println(s"  def decode(bytes: Array[Byte], offset: Int): Decoded[($ts)] = {")
  for (i <- range) {
    writer.println(s"    val v$i = if (type$i.dynamic) {")
    writer.println(s"      val bytesOffset = Uint256Type.decode(bytes, offset + headOffset(${i - 1})).value.intValue()")
    writer.println(s"      type$i.decode(bytes, offset + bytesOffset)")
    writer.println(s"    } else {")
    writer.println(s"      type$i.decode(bytes, offset + headOffset(${i - 1}))")
    writer.println(s"    } ")
  }
  writer.println(s"    Decoded((${range.map(i => s"v$i.value").mkString(", ")}), v$arity.offset)")
  writer.println(s"  }")
  writer.println(s"}")
  writer.println()
  writer.println(s"object Tuple${arity}Type {")
  writer.println(s"  def apply[$ts](${range.map(i => s"type$i: Type[T$i]").mkString(", ")}): Tuple${arity}Type[$ts] = ")
  writer.println(s"    new Tuple${arity}Type(${range.map(i => s"type$i").mkString(", ")})")
  writer.println(s"}")
}

for (i <- 2 to 22) {
  val f = new File(s"src/main/scala/scalether/abi/tuple/Tuple${i}Type.scala")
  val out = new FileOutputStream(f)
  val writer = new PrintWriter(out)
  generate(i, writer)
  writer.flush()
  writer.close()
  out.close()
}
