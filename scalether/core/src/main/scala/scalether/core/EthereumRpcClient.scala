package scalether.core

import cats.MonadError
import io.daonomic.rpc.RpcHttpClient
import io.daonomic.rpc.JsonConverter
import io.daonomic.rpc.RpcTransport
import scalether.core.json.EthereumJacksonModule

import scala.language.higherKinds

class EthereumRpcClient[F[_]](transport: RpcTransport[F])
                          (implicit me: MonadError[F, Throwable])
  extends RpcHttpClient[F](EthereumRpcClient.jsonConverter, transport) {

}

object EthereumRpcClient {
  def jsonConverter: JsonConverter = new JsonConverter(new EthereumJacksonModule)
}
