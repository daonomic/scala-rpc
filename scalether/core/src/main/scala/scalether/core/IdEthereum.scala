package scalether.core

import java.math.BigInteger

import io.daonomic.cats.implicits._
import cats.Id
import io.daonomic.rpc.domain.{Binary, Word}
import io.daonomic.rpc.{IdRpcTransport, RpcException}
import scalether.domain.Address
import scalether.domain.request.{LogFilter, Transaction}
import scalether.domain.response
import scalether.domain.response.{Block, Log, TransactionReceipt}

class IdEthereum(transport: IdRpcTransport) extends Ethereum[Id](transport) {
  @throws[RpcException]
  override def netVersion(): String = super.netVersion()

  @throws[RpcException]
  override def web3ClientVersion(): String = super.web3ClientVersion()

  @throws[RpcException]
  override def web3Sha3(data: String): String = super.web3Sha3(data)

  @throws[RpcException]
  override def netListening(): Boolean = super.netListening()

  @throws[RpcException]
  override def ethBlockNumber(): BigInteger = super.ethBlockNumber()

  @throws[RpcException]
  override def ethGetBlockByHash(hash: Word): Block = super.ethGetBlockByHash(hash)

  @throws[RpcException]
  override def ethGetBlockByNumber(number: BigInteger): Block = super.ethGetBlockByNumber(number)

  @throws[RpcException]
  override def ethCall(transaction: Transaction, defaultBlockParameter: String): Binary = super.ethCall(transaction, defaultBlockParameter)

  @throws[RpcException]
  override def ethEstimateGas(transaction: Transaction, defaultBlockParameter: String): BigInteger = super.ethEstimateGas(transaction, defaultBlockParameter)

  @throws[RpcException]
  override def ethSendTransaction(transaction: Transaction): Word = super.ethSendTransaction(transaction)

  @throws[RpcException]
  override def ethSendRawTransaction(transaction: Binary): Word = super.ethSendRawTransaction(transaction)

  @throws[RpcException]
  override def ethGetTransactionReceipt(hash: Word): Option[TransactionReceipt] = super.ethGetTransactionReceipt(hash)

  @throws[RpcException]
  override def ethGetTransactionByHash(hash: Word): Option[response.Transaction] = super.ethGetTransactionByHash(hash)

  @throws[RpcException]
  override def netPeerCount(): BigInteger = super.netPeerCount()

  @throws[RpcException]
  override def ethGetTransactionCount(address: Address, defaultBlockParameter: String): BigInteger = super.ethGetTransactionCount(address, defaultBlockParameter)

  @throws[RpcException]
  override def ethGetBalance(address: Address, defaultBlockParameter: String): BigInteger = super.ethGetBalance(address, defaultBlockParameter)

  @throws[RpcException]
  override def ethGasPrice(): BigInteger = super.ethGasPrice()

  @throws[RpcException]
  override def ethGetLogs(filter: LogFilter): List[Log] = super.ethGetLogs(filter)

  @throws[RpcException]
  override def ethNewFilter(filter: LogFilter): BigInteger = super.ethNewFilter(filter)

  @throws[RpcException]
  override def ethGetFilterChanges(id: BigInteger): List[Log] = super.ethGetFilterChanges(id)

  @throws[RpcException]
  override def ethGetCode(address: Address, defaultBlockParameter: String): Binary = super.ethGetCode(address, defaultBlockParameter)

  @throws[RpcException]
  override def exec[T](method: String, params: Any*)(implicit mf: Manifest[T]): T = super.exec(method, params:_*)

  @throws[RpcException]
  override def execOption[T](method: String, params: Any*)(implicit mf: Manifest[T]): Option[T] = super.execOption(method, params:_*)
}
