import Dependencies._

bintrayPackage := "scala-rpc-transport-mono"

libraryDependencies ++= Seq(
  reactor,
  reactorNetty,
  springWebFlux
)
