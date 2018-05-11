Scalether is part of scala-rpc project
See https://github.com/daonomic/scala-rpc for basic info

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
