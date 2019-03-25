package io.daonomic.bitcoin.rpc.core

import io.daonomic.bitcoin.rpc.domain.{Block, Transaction}
import io.daonomic.rpc.HttpTransport
import io.daonomic.rpc.domain.Binary

import scala.language.higherKinds

class RestBitcoind[F[_]](transport: HttpTransport[F]) {
  def getBlockSimple(hash: String): F[Block[Binary]] =
    transport.get(s"/rest/block/notxdetails/$hash.json")

  def getBlockFull(hash: String): F[Block[Transaction]] =
    transport.get(s"/rest/block/$hash.json")
}
