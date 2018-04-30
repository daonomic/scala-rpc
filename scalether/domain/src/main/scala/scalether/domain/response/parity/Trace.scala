package scalether.domain.response.parity

import java.math.BigInteger

import scalether.domain.{Address, Word}

case class Trace(action: Action,
                 blockHash: Word,
                 blockNumber: BigInteger,
                 result: ActionResult,
                 subtraces: Int,
                 traceAddress: List[Int],
                 transactionHash: Word,
                 transactionPosition: Int,
                 error: String,
                 `type`: String)

case class Action(callType: String, from: Address, gas: BigInteger, input: String, to: Address, value: BigInteger)

case class ActionResult(gasUsed: BigInteger, output: String, address: Address)