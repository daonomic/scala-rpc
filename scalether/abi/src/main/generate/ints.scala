for(i <- 1 to 32) {
  println(s"object Uint${i * 8}Type extends UintType(${i * 8})")
}

println()

for(i <- 1 to 32) {
  println(s"object Int${i * 8}Type extends IntType(${i * 8})")
}

println()

for(i <- 1 to 32) {
  println(s"object Bytes${i}Type extends FixedBytesType($i)")
}
