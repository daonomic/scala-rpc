package scalether.domain.response

import java.math.BigInteger

import io.daonomic.rpc.domain.{Binary, Word}
import scalether.domain.Address

case class Log(logIndex: BigInteger,
               transactionIndex: BigInteger,
               transactionHash: Word,
               blockHash: Word,
               blockNumber: BigInteger,
               address: Address,
               data: Binary,
               removed: Boolean,
               topics: List[Word],
               `type`: String)
