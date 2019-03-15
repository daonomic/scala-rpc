package io.daonomic.bitcoin.rpc.core

import io.daonomic.bitcoin.rpc.domain.{Block, Transaction}
import io.daonomic.rpc.HttpTransport

class RestBitcoind[F[_]](transport: HttpTransport[F]) {
  def getBlockSimple(hash: String): F[Block[String]] =
    transport.get(s"/rest/block/notxdetails/$hash.json")

  def getBlockFull(hash: String): F[Block[Transaction]] =
    transport.get(s"/rest/block/$hash.json")
}
