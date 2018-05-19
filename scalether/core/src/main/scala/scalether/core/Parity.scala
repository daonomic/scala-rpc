package scalether.core

import java.math.BigInteger

import io.daonomic.cats.MonadThrowable
import io.daonomic.rpc.RpcTransport
import scalether.domain.response.parity.Trace

import scala.language.higherKinds

class Parity[F[_]](transport: RpcTransport[F])
                  (implicit me: MonadThrowable[F])
  extends EthereumRpcClient[F](transport) {

  def traceTransaction(txHash: String): F[List[Trace]] = {
    exec("trace_transaction", txHash)
  }

  def traceBlock(blockNumber: BigInteger): F[List[Trace]] = {
    exec("trace_block", blockNumber)
  }

}
