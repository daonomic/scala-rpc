# scala-rpc (Bitcoin and Ethereum JSON RPC client)
Scala client library for different blockchain nodes. Currently, Bitcoin and Ethereum are supported
Litecoin, Bitcoin cash and other Bitcoin-like nodes should be supported as well

This is RPC client library for connecting to bitcoind and geth/parity nodes. Please, consider this library as experimental. API in future can be changed. 

This library is not replacement of Bitcoin/Ethereum nodes. It connects to nodes using http json rpc protocol.

## Supports non-blocking IO
Different http transport libraries are supported. Currently there are implementations using:
* Spring's WebClient (for non-blocking IO). 
* scalaj-http for blocking IO

Library uses Typelevel's https://github.com/typelevel/cats to support different types of results (Try for blocking API and Mono for Spring's WebClient)

## Features
Library is modular and provides different layers:
* thin layer over JSON RPC
* different transaction senders for Ethereum (you can use the same API for sending transactions and sign them in the node or sign them right in Java code, from your client)
* common blockchain listeners for Bitcoin/Ethereum: you can listen to different events using the same api for both blockchains
* contract wrappers for Ethereum (https://github.com/daonomic/scalether-maven-plugin can be used to generate contract wrappers)

## Adding dependencies (Maven)
1. Add bintray daonomic repository
```xml
<repositories>
    <repository>
        <id>daonomic-bintray</id>
        <name>daonomic-bintray</name>
        <url>https://dl.bintray.com/daonomic/maven/</url>
    </repository>
</repositories>

```
2. Add scalether dependency 
```xml
<dependency>
    <groupId>io.daonomic.scalether</groupId>
    <artifactId>contract_2.12</artifactId>
    <version>${scalether.version}</version>
</dependency>

```

## Adding dependencies (sbt)
1. Add bintray daonomic repository
```scala
resolvers += Resolver.bintrayRepo("daonomic", "maven")
```
2. Add scalether dependency
```scala
libraryDependencies += "io.daonomic.scalether" %% "contract" % scaletherVersion
```

## Writing some code (WIP)
