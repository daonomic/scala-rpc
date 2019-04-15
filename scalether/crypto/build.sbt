import Dependencies._

name := "crypto"
libraryDependencies += bouncyCastle
libraryDependencies += junit % "test"

autoScalaLibrary := false
crossPaths := false