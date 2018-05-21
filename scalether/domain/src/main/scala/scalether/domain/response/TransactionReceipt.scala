package scalether.domain.response

import java.math.BigInteger

import io.daonomic.rpc.domain.Word
import scalether.domain.Address

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
