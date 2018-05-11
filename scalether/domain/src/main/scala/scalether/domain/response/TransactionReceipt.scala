package scalether.domain.response

import java.math.BigInteger

import scalether.domain.{Address, Word}

case class TransactionReceipt(transactionHash: Word,
                              transactionIndex: BigInteger,
                              blockHash: Word,
                              blockNumber: BigInteger,
                              cumulativeGasUsed: BigInteger,
                              gasUsed: BigInteger,
                              contractAddress: Address,
                              status: BigInteger,
                              from: Address,
                              to: Address,
                              logs: List[Log]) {
  def success: Boolean = status == BigInteger.ONE
}
