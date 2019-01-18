package scalether.core

import java.math.BigInteger
import java.util

import io.daonomic.cats.implicits._
import io.daonomic.rpc.MonoRpcTransport
import io.daonomic.rpc.domain.{Binary, Word}
import reactor.core.publisher.Mono
import scalether.domain.request.{LogFilter, Transaction}
import scalether.domain.response.{Block, Log, TransactionReceipt}
import scalether.domain.{Address, response}

import scala.collection.JavaConverters._

class MonoEthereum(transport: MonoRpcTransport)
  extends Ethereum[Mono](transport) {

  override def web3ClientVersion(): Mono[String] =
    super.web3ClientVersion()

  override def web3Sha3(data: String): Mono[String] =
    super.web3Sha3(data)

  override def netVersion(): Mono[String] =
    super.netVersion()

  override def netListening(): Mono[Boolean] =
    super.netListening()

  override def ethBlockNumber(): Mono[BigInteger] =
    super.ethBlockNumber()

  override def ethGetBlockByHash(hash: Word): Mono[Block[Word]] =
    super.ethGetBlockByHash(hash)

  override def ethGetFullBlockByHash(hash: Word): Mono[Block[response.Transaction]] =
    super.ethGetFullBlockByHash(hash)

  override def ethGetBlockByNumber(number: BigInteger): Mono[Block[Word]] =
    super.ethGetBlockByNumber(number)

  override def ethGetFullBlockByNumber(number: BigInteger): Mono[Block[response.Transaction]] =
    super.ethGetFullBlockByNumber(number)

  override def ethCall(transaction: Transaction, defaultBlockParameter: String): Mono[Binary] =
    super.ethCall(transaction, defaultBlockParameter)

  override def ethSendTransaction(transaction: Transaction): Mono[Word] =
    super.ethSendTransaction(transaction)

  override def ethSendRawTransaction(transaction: Binary): Mono[Word] =
    super.ethSendRawTransaction(transaction)

  override def ethGetTransactionReceipt(hash: Word): Mono[Option[TransactionReceipt]] =
    super.ethGetTransactionReceipt(hash)

  override def ethGetTransactionByHash(hash: Word): Mono[Option[response.Transaction]] =
    super.ethGetTransactionByHash(hash)

  override def ethGetTransactionCount(address: Address, defaultBlockParameter: String): Mono[BigInteger] =
    super.ethGetTransactionCount(address, defaultBlockParameter)

  override def netPeerCount(): Mono[BigInteger] =
    super.netPeerCount()

  override def ethGetBalance(address: Address, defaultBlockParameter: String): Mono[BigInteger] =
    super.ethGetBalance(address, defaultBlockParameter)

  override def ethGasPrice(): Mono[BigInteger] =
    super.ethGasPrice()

  override def ethGetLogs(filter: LogFilter): Mono[List[Log]] =
    super.ethGetLogs(filter)

  override def ethNewFilter(filter: LogFilter): Mono[BigInteger] =
    super.ethNewFilter(filter)

  override def ethGetFilterChanges(id: BigInteger): Mono[List[Log]] =
    super.ethGetFilterChanges(id)

  def ethGetLogsJava(filter: LogFilter): Mono[util.List[Log]] =
    super.ethGetLogs(filter).map(_.asJava)

  def ethGetFilterChangesJava(id: BigInteger): Mono[util.List[Log]] =
    super.ethGetFilterChanges(id).map(_.asJava)

  override def ethGetCode(address: Address, defaultBlockParameter: String): Mono[Binary] =
    super.ethGetCode(address, defaultBlockParameter)

  override def ethEstimateGas(transaction: Transaction, defaultBlockParameter: String): Mono[BigInteger] =
    super.ethEstimateGas(transaction, defaultBlockParameter)

  override def exec[T](method: String, params: Any*)(implicit mf: Manifest[T]): Mono[T] = super.exec(method, params:_*)

  override def execOption[T](method: String, params: Any*)(implicit mf: Manifest[T]): Mono[Option[T]] = super.execOption(method, params:_*)
}
