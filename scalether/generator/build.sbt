//plain java project so there is no need to include scala-library in maven plugin
import Dependencies._

name := "generator"

libraryDependencies += freemarker
libraryDependencies += beanutils
libraryDependencies += jackson
libraryDependencies += bouncyCastle

libraryDependencies += "org.scala-lang" % "scala-library" % fullScalaVersion % "test"

autoScalaLibrary := false
crossPaths := false