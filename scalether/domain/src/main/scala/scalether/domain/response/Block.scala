package scalether.domain.response

import java.math.BigInteger

import scalether.domain.{Address, Binary, Word}

case class Block(number: BigInteger,
                 hash: Word,
                 parentHash: Word,
                 nonce: String,
                 sha3Uncles: String,
                 logsBloom: String,
                 transactionsRoot:String,
                 stateRoot: String,
                 miner: Address,
                 difficulty: BigInteger,
                 totalDifficulty: BigInteger,
                 extraData: Binary,
                 size: BigInteger,
                 gasLimit: BigInteger,
                 gasUsed: BigInteger,
                 transactions: List[Word],
                 timestamp: BigInteger)
