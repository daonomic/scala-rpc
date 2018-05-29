package io.daonomic.bitcoin.rpc.core

import java.math.BigInteger

import io.daonomic.bitcoin.rpc.domain.{Block, Transaction}
import io.daonomic.cats.implicits._
import io.daonomic.rpc.MonoRpcTransport
import reactor.core.publisher.Mono

class MonoBitcoind(transport: MonoRpcTransport) extends Bitcoind[Mono](transport) {

  override def help(what: String*): Mono[String] =
    super.help(what:_*)

  override def getBlockCount: Mono[BigInteger] =
    super.getBlockCount

  override def getNewAddress: Mono[String] =
    super.getNewAddress

  override def generate(amount: Int): Mono[List[String]] =
    super.generate(amount)

  override def sendToAddress(to: String, amount: Double): Mono[String] =
    super.sendToAddress(to, amount)

  override def getRawTransaction(txid: String, verbose: Boolean): Mono[Transaction] =
    super.getRawTransaction(txid, verbose)

  override def importAddress(address: String, label: String, rescan: Boolean, p2sh: Boolean): Mono[Unit] =
    super.importAddress(address, label, rescan, p2sh)

  def importAddressVoid(address: String, label: String, rescan: Boolean, p2sh: Boolean): Mono[Void] =
    super.importAddress(address, label, rescan, p2sh).`then`()

  override def getBlockHash(blockNumber: BigInteger): Mono[String] =
    super.getBlockHash(blockNumber)

  override def getBlockSimple(hash: String): Mono[Block[String]] =
    super.getBlockSimple(hash)

  override def getBlockFull(hash: String): Mono[Block[Transaction]] =
    super.getBlockFull(hash)
}
