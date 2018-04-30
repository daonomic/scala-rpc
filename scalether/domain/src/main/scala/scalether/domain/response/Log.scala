package scalether.domain.response

import java.math.BigInteger

import scalether.domain.{Address, Binary, Word}

case class Log(logIndex: BigInteger,
               transactionIndex: BigInteger,
               transactionHash: Word,
               blockHash: Word,
               blockNumber: BigInteger,
               address: Address,
               data: Binary,
               topics: List[Word],
               `type`: String)
