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

### Basic operations

```scala
//create transport and rpc client
val ethereum = new Ethereum(new ScalajHttpTransport("http://localhost:8545"))
//get current block number and print it
println(ethereum.ethBlockNumber())
//get account balance
println(ethereum.ethGetBalance(Address("0x7acbc0b5c51017dc659a19f257bb3e462309b626"), "latest"))
```

### Transaction sender
[TransactionSender](https://github.com/daonomic/scala-rpc/blob/master/scalether/transaction/src/main/scala/scalether/transaction/TransactionSender.scala) is an object responsible for sending transactions to Ethereum network. Currently there are two transaction senders:
- [SimpleTransactionSender](https://github.com/daonomic/scala-rpc/blob/master/scalether/transaction/src/main/scala/scalether/transaction/SimpleTransactionSender.scala) (it relies on the node's wallet and RPC method "eth_sendTransaction")
- [SigningTransactionSender](https://github.com/daonomic/scala-rpc/blob/master/scalether/transaction/src/main/scala/scalether/transaction/SigningTransactionSender.scala) - Use this transaction sender to sign transactions right in Java code. You have to provide your private key to sign transactions. SigningTransactionSender signs transaction and sends it to the network using "eth_sendRawTransaction" RPC method

Here is an example for SigningTransactionSender:

```scala
  val sender = new SigningTransactionSender[Try](
    ethereum,
    new SimpleNonceProvider[Try](ethereum),
    utils.Numeric.toBigInt("0x00120de4b1518cf1f16dc1b02f6b4a8ac29e870174cb1d8575f578480930250a"),
    2000000, //gas
    new ValGasPriceProvider[Try](10)
  )
  sender.sendTransaction(Transaction(to = address, value = value))
```

There are some objects needed for SigningTransactionSender to work properly:
- ethereum - RPC client
- NonceProvider should calculate current nonce for account (nonce can be specified in transaction object, then NonceProvider is not used)
- privateKey - your account's private key
- gas - max gas for transaction (it can be specified in transaction, then this value won't be used)
- GasPriceProvider should return gasPrice for the transaction (see [NodeGasPriceProvider](https://github.com/daonomic/scala-rpc/blob/master/scalether/transaction/src/main/scala/scalether/transaction/NodeGasPriceProvider.scala) and [ValGasPriceProvider](https://github.com/daonomic/scala-rpc/blob/master/scalether/transaction/src/main/scala/scalether/transaction/ValGasPriceProvider.scala))
