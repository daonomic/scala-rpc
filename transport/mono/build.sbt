bintrayPackage := "scala-rpc-transport-mono"

libraryDependencies += "io.projectreactor" % "reactor-core" % Versions.reactor
libraryDependencies += "io.projectreactor.ipc" % "reactor-netty" % Versions.reactorNetty
libraryDependencies += "org.springframework" % "spring-webflux" % Versions.spring
