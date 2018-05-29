package io.daonomic.bitcoin.rpc.core

import java.math.BigInteger

import cats.implicits._
import io.daonomic.bitcoin.rpc.domain.{Block, Transaction}
import io.daonomic.cats.MonadThrowable
import io.daonomic.rpc.{JsonConverter, RpcHttpClient, RpcTransport}

import scala.language.higherKinds

class Bitcoind[F[_]](transport: RpcTransport[F])
                    (implicit me: MonadThrowable[F])
  extends RpcHttpClient[F](new JsonConverter(), transport) {

  def help(what: String*): F[String] =
    exec("help", what: _*)

  def getBlockCount: F[BigInteger] =
    exec("getblockcount")

  def getNewAddress: F[String] =
    exec("getnewaddress")

  def generate(amount: Int): F[List[String]] =
    exec("generate", amount)

  def sendToAddress(to: String, amount: Double): F[String] =
    exec("sendtoaddress", to, amount)

  def getRawTransaction(txid: String, verbose: Boolean = false): F[Transaction] =
    exec("getrawtransaction", txid, verbose)

  def importAddress(address: String, label: String = "", rescan: Boolean = false, p2sh: Boolean = false): F[Unit] =
    execOption[String]("importaddress", address, label, rescan, p2sh).map(_ => ())

  def getBlockHash(blockNumber: BigInteger): F[String] =
    exec("getblockhash", blockNumber)

  def getBlockSimple(hash: String): F[Block[String]] =
    get(s"/rest/block/notxdetails/$hash.json")

  def getBlockFull(hash: String): F[Block[Transaction]] =
    get(s"/rest/block/$hash.json")
}
